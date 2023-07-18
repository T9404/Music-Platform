package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;
import ru.cft.shift.intensive.template.entity.SignerByName;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapper;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapperImpl;
import ru.cft.shift.intensive.template.repository.AlbumByGenreRepository;
import ru.cft.shift.intensive.template.repository.AlbumBySignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SignerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumByGenreRepository albumByGenreRepository;
    private final AlbumBySignerRepository albumBySignerRepository;
    private final SignerService signerService;
    private final AlbumMapper albumMapper = new AlbumMapperImpl();

    @Autowired
    public AlbumServiceImpl(AlbumByGenreRepository albumByGenreRepository,
                            AlbumBySignerRepository albumBySignerRepository, @Lazy SignerService signerService) {
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
        AlbumBySinger albumBySinger = albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(singerName, albumName)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
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
        AlbumBySinger albumBySinger = albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(singerName, albumName)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
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
    public void deleteSignerAlbums(String singerName) {
        List<AlbumBySinger> albums = albumBySignerRepository.findAllByKey_SingerName(singerName)
                .orElseThrow(() -> new IllegalArgumentException("Singer not found"));
        albumBySignerRepository.deleteAll(albums);
        albumByGenreRepository.deleteAll(albums.stream()
                .map(albumMapper::albumBySignerToAlbumByGenre)
                .collect(Collectors.toList()));
    }
}
