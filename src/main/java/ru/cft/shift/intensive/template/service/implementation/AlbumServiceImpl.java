package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.*;
import ru.cft.shift.intensive.template.exception.AlbumAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.AlbumNotFoundException;
import ru.cft.shift.intensive.template.exception.SingerNotFoundException;
import ru.cft.shift.intensive.template.exception.SongNotFoundException;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapper;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapperImpl;
import ru.cft.shift.intensive.template.repository.AlbumByGenreRepository;
import ru.cft.shift.intensive.template.repository.AlbumBySignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SingerService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumByGenreRepository albumByGenreRepository;
    private final AlbumBySignerRepository albumBySignerRepository;
    private final SingerService singerService;
    private final AlbumMapper albumMapper = new AlbumMapperImpl();

    @Autowired
    public AlbumServiceImpl(AlbumByGenreRepository albumByGenreRepository,
                            AlbumBySignerRepository albumBySignerRepository,
                            @Lazy SingerService singerService) {
        this.albumByGenreRepository = albumByGenreRepository;
        this.albumBySignerRepository = albumBySignerRepository;
        this.singerService = singerService;
    }

    @Override
    public List<AlbumDto> getList() {
        return albumByGenreRepository.findAll().stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAlbumExists(String albumName, String singerName) {
        return albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(singerName, albumName)
                .isPresent();
    }

    @Override
    public AlbumDto create(Album album) {
        checkAlbumNotExists(album);
        saveAlbumBySigner(album);
        saveAlbumByGenre(album);
        updateIfSignerNotExist(album);
        return albumMapper.entityToAlbumDto(album);
    }

    private void checkAlbumNotExists(Album album) {
        if (isAlbumExists(album.getName(), album.getOwner())) {
            throw new AlbumAlreadyExistsException();
        }
    }

    private void saveAlbumBySigner(Album album) {
        albumBySignerRepository.save(albumMapper.albumToAlbumBySinger(album));
    }

    private void saveAlbumByGenre(Album album) {
        albumByGenreRepository.save(albumMapper.albumToAlbumByGenre(album));
    }

    private void updateIfSignerNotExist(Album album) {
        if (!singerService.isSignerExists(album.getOwner())) {
            Singer singer = setAssociationToSigner(album);
            singerService.create(singer);
        }
    }

    private Singer setAssociationToSigner(Album album) {
        Singer singer = new Singer();
        singer.setName(album.getOwner());
        singer.getAlbums().add(album);
        return singer;
    }

    @Override
    public void deleteAlbum(String albumName, String singerName) {
        AlbumBySinger albumBySinger = getAlbumBySinger(albumName, singerName);
        albumBySignerRepository.delete(albumBySinger);
        albumByGenreRepository.delete(albumMapper.albumBySignerToAlbumByGenre(albumBySinger));
    }

    @Override
    public List<AlbumDto> getSignerAlbums(String singerName) {
        return albumBySignerRepository.findAllByKey_SingerName(singerName)
                .orElseThrow(SingerNotFoundException::new)
                .stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlbumDto> getAlbumsByGenre(String genre) {
        List<AlbumByGenre> albums = albumByGenreRepository.findAllByKey_Genre(genre);
        initializeSongsForAlbumsByGenre(albums);
        return albums.stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toList());
    }

    private void initializeSongsForAlbumsByGenre(List<AlbumByGenre> albums) {
        albums.forEach(albumByGenre -> {
            if (albumByGenre.getSongs() == null) {
                albumByGenre.setSongs(new HashSet<>());
            }
        });
    }

    @Override
    public void deleteSignerAlbums(String singerName) {
        List<AlbumBySinger> albums = albumBySignerRepository.findAllByKey_SingerName(singerName)
                .orElseThrow(SingerNotFoundException::new);
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
                .orElseThrow(AlbumNotFoundException::new);
        initializeSongBySigner(albumBySinger);
        return albumBySinger;
    }

    private void initializeSongBySigner(AlbumBySinger albumBySinger) {
        if (albumBySinger.getSongs() == null) {
            albumBySinger.setSongs(new HashSet<>());
        }
    }

    @Override
    public void deleteSong(String albumName, String ownerName, String songName) {
        AlbumBySinger albumBySinger = getAlbumBySinger(albumName, ownerName);
        Song song = albumBySinger.getSongs().stream()
                .filter(name -> name.getName().equals(songName))
                .findFirst()
                .orElseThrow(SongNotFoundException::new);
        updateAlbumsAfterDeletingSong(albumBySinger, song);
    }

    private void updateAlbumsAfterDeletingSong(AlbumBySinger albumBySinger, Song song) {
        albumBySinger.removeSong(song);
        albumBySignerRepository.save(albumBySinger);
        AlbumByGenre albumByGenre = albumMapper.albumBySignerToAlbumByGenre(albumBySinger);
        albumByGenre.getSongs().remove(song);
        albumByGenreRepository.save(albumByGenre);
    }
}
