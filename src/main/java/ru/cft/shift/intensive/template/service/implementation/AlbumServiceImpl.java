package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.dto.SongDto;
import ru.cft.shift.intensive.template.entity.*;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapper;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapperImpl;
import ru.cft.shift.intensive.template.repository.AlbumByGenreRepository;
import ru.cft.shift.intensive.template.repository.AlbumBySignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SignerService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumByGenreRepository albumByGenreRepository;
    private final AlbumBySignerRepository albumBySignerRepository;
    private final SignerService signerService;
    private final AlbumMapper albumMapper = new AlbumMapperImpl();

    @Autowired
    public AlbumServiceImpl(AlbumByGenreRepository albumByGenreRepository,
                            AlbumBySignerRepository albumBySignerRepository,
                            @Lazy SignerService signerService) {
        this.albumByGenreRepository = albumByGenreRepository;
        this.albumBySignerRepository = albumBySignerRepository;
        this.signerService = signerService;
    }

    @Override
    public List<AlbumDto> getList() {
        return albumByGenreRepository.findAll().stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumDto getAlbum(String albumName, String singerName) {
        AlbumBySinger albumBySinger = getAlbumBySinger(albumName, singerName);
        return albumMapper.entityToAlbumDto(albumBySinger);
    }

    @Override
    public boolean isAlbumExists(String albumName, String singerName) {
        return albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(singerName, albumName)
                .isPresent();
    }

    @Override
    public AlbumDto create(Album album) {
        if (isAlbumExists(album.getName(), album.getOwner())) {
            throw new IllegalArgumentException("Album already exists");
        }
        AlbumBySinger albumBySinger = albumMapper.albumToAlbumBySinger(album);
        albumBySignerRepository.save(albumBySinger);
        AlbumByGenre albumByGenre = albumMapper.albumToAlbumByGenre(album);
        albumByGenreRepository.save(albumByGenre);
        updateSigner(album);
        return albumMapper.entityToAlbumDto(albumByGenre);
    }

    private void updateSigner(Album album) {
        if (!signerService.isSignerExists(album.getOwner())) {
            SignerByName signerByName = new SignerByName();
            signerByName.setName(album.getOwner());
            signerByName.getAlbums().add(album);
            signerService.create(signerByName);
        }
    }

    @Override
    public void delete(String albumName, String singerName) {
        AlbumBySinger albumBySinger = getAlbumBySinger(albumName, singerName);
        albumBySignerRepository.delete(albumBySinger);
        albumByGenreRepository.delete(albumMapper.albumBySignerToAlbumByGenre(albumBySinger));
    }

    @Override
    public List<AlbumDto> getSignerAlbums(String singerName) {
        return albumBySignerRepository.findAllByKey_SingerName(singerName)
                .orElseThrow(() -> new IllegalArgumentException("Singer not found"))
                .stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> getAlbumsByGenre(String genre) {
        System.out.println("1");
        List<AlbumByGenre> albums = albumByGenreRepository.findAllByKey_Genre(genre)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found"));
        System.out.println("1");
        // if albumByGenre is null = create new HashSet
        albums.forEach(albumByGenre -> {
            if (albumByGenre.getSongs() == null) {
                albumByGenre.setSongs(new HashSet<>());
            }
        });
        return albums.stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSignerAlbums(String singerName) {
        List<AlbumBySinger> albums = albumBySignerRepository.findAllByKey_SingerName(singerName)
                .orElseThrow(() -> new IllegalArgumentException("Singer not found"));
        albumBySignerRepository.deleteAll(albums);
        albumByGenreRepository.deleteAll(albums.stream()
                .map(albumMapper::albumBySignerToAlbumByGenre)
                .collect(Collectors.toList()));
    }

    @Override
    public AlbumDto addSong(String albumName, Song song) {
        AlbumBySinger albumBySinger = getAlbumBySinger(albumName, song.getOwner());
        albumBySinger.addSong(song);
        albumBySignerRepository.save(albumBySinger);
        albumByGenreRepository.save(albumMapper.albumBySignerToAlbumByGenre(albumBySinger));
        return albumMapper.entityToAlbumDto(albumBySinger);
    }

    private AlbumBySinger getAlbumBySinger(String albumName, String ownerName) {
        AlbumBySinger albumBySinger = albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(ownerName, albumName)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
        if (albumBySinger.getSongs() == null) {
            albumBySinger.setSongs(new HashSet<>());
        }
        return albumBySinger;
    }

    @Override
    public void deleteSong(String albumName, String ownerName, String songName) {
        AlbumBySinger albumBySinger = getAlbumBySinger(albumName, ownerName);
        Song song = albumBySinger.getSongs().stream()
                .filter(name -> name.getName().equals(songName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));
        albumBySinger.removeSong(song);
        albumBySignerRepository.save(albumBySinger);
        AlbumByGenre albumByGenre = albumMapper.albumBySignerToAlbumByGenre(albumBySinger);
        albumByGenre.getSongs().remove(song);
        albumByGenreRepository.save(albumByGenre);
    }
}
