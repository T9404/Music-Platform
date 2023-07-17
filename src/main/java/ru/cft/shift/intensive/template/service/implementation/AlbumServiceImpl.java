package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapper;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapperImpl;
import ru.cft.shift.intensive.template.repository.AlbumByGenreRepository;
import ru.cft.shift.intensive.template.repository.AlbumBySignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumByGenreRepository albumByGenreRepository;
    private final AlbumBySignerRepository albumBySignerRepository;
    private final AlbumMapper albumMapper = new AlbumMapperImpl();

    @Autowired
    public AlbumServiceImpl(AlbumByGenreRepository albumByGenreRepository, AlbumBySignerRepository albumBySignerRepository) {
        this.albumByGenreRepository = albumByGenreRepository;
        this.albumBySignerRepository = albumBySignerRepository;
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
    public boolean isExists(String albumName, String singerName) {
        return albumBySignerRepository.findByKey_SingerNameAndKey_AlbumName(singerName, albumName).isPresent();
    }

    @Override
    public AlbumDto create(Album album) {
        AlbumBySinger albumBySinger = albumMapper.albumToAlbumBySinger(album);
        albumBySignerRepository.save(albumBySinger);
        AlbumByGenre albumByGenre = albumMapper.albumToAlbumByGenre(album);
        albumByGenreRepository.save(albumByGenre);
        return albumMapper.entityToAlbumDto(albumByGenre);
    }

    @Override
    public void delete(String albumName, String singerName) {
        AlbumBySinger albumBySinger = albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(singerName, albumName)
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));
        albumBySignerRepository.delete(albumBySinger);
        albumByGenreRepository.delete(albumMapper.albumBySignerToAlbumByGenre(albumBySinger));
    }
}
