package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Singer;
import ru.cft.shift.intensive.template.exception.SingerAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.SingerNotFoundException;
import ru.cft.shift.intensive.template.mapper.singer.SingerMapper;
import ru.cft.shift.intensive.template.mapper.singer.SingerMapperImpl;
import ru.cft.shift.intensive.template.repository.SingerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SingerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SingerServiceImpl implements SingerService {
    private final SingerRepository singerRepository;
    private final AlbumService albumService;
    private final PasswordEncoder passwordEncoder;
    private final SingerMapper singerMapper = new SingerMapperImpl();

    @Autowired
    public SingerServiceImpl(SingerRepository singerRepository, @Lazy AlbumService albumService, PasswordEncoder passwordEncoder) {
        this.singerRepository = singerRepository;
        this.albumService = albumService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<SingerDto> getList() {
        return singerRepository.findAll().stream()
                .map(singerMapper::entityToSignerDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSignerExists(String signerName) {
        return singerRepository.findById(signerName).isPresent();
    }

    @Override
    public SingerDto findBySignerName(String signerName) {
        return singerRepository.findById(signerName)
                .map(singerMapper::entityToSignerDto)
                .orElseThrow(SingerNotFoundException::new);
    }

    @Override
    public SingerDto create(Singer singer) {
        checkSignerNotExists(singer.getName());
        singer.setPassword(passwordEncoder.encode(singer.getPassword()));
        saveSigner(singer);
        updateAlbums(singer);
        return singerMapper.entityToSignerDto(singer);
    }

    private void checkSignerNotExists(String name) {
        if (isSignerExists(name)) {
            throw new SingerAlreadyExistsException();
        }
    }

    private void saveSigner(Singer singer) {
        singerRepository.save(singer);
    }

    private void updateAlbums(Singer singer) {
        for (Album album : singer.getAlbums()) {
            if (!albumService.isAlbumExists(album.getName(), singer.getName())) {
                albumService.create(album);
            }
        }
    }

    @Override
    public void delete(String signerName) {
        Singer singer = singerRepository.findById(signerName)
                .orElseThrow(SingerNotFoundException::new);
        albumService.deleteSignerAlbums(signerName);
        singerRepository.delete(singer);
    }
}
