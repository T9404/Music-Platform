package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Signer;
import ru.cft.shift.intensive.template.exception.SignerAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.SignerNotFoundException;
import ru.cft.shift.intensive.template.mapper.signer.SignerMapper;
import ru.cft.shift.intensive.template.mapper.signer.SignerMapperImpl;
import ru.cft.shift.intensive.template.repository.SignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SignerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignerServiceImpl implements SignerService {
    private final SignerRepository signerRepository;
    private final AlbumService albumService;
    private final PasswordEncoder passwordEncoder;
    private final SignerMapper signerMapper = new SignerMapperImpl();

    @Autowired
    public SignerServiceImpl(SignerRepository signerRepository, @Lazy AlbumService albumService, PasswordEncoder passwordEncoder) {
        this.signerRepository = signerRepository;
        this.albumService = albumService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<SignerDto> getList() {
        return signerRepository.findAll().stream()
                .map(signerMapper::entityToSignerDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSignerExists(String signerName) {
        return signerRepository.findById(signerName).isPresent();
    }

    @Override
    public SignerDto findBySignerName(String signerName) {
        return signerRepository.findById(signerName)
                .map(signerMapper::entityToSignerDto)
                .orElseThrow(SignerNotFoundException::new);
    }

    @Override
    public SignerDto create(Signer signer) {
        checkSignerNotExists(signer.getName());
        signer.setPassword(passwordEncoder.encode(signer.getPassword()));
        saveSigner(signer);
        updateAlbums(signer);
        return signerMapper.entityToSignerDto(signer);
    }

    private void checkSignerNotExists(String name) {
        if (isSignerExists(name)) {
            throw new SignerAlreadyExistsException();
        }
    }

    private void saveSigner(Signer signer) {
        signerRepository.save(signer);
    }

    private void updateAlbums(Signer signer) {
        for (Album album : signer.getAlbums()) {
            if (!albumService.isAlbumExists(album.getName(), signer.getName())) {
                albumService.create(album);
            }
        }
    }

    @Override
    public void delete(String signerName) {
        Signer signer = signerRepository.findById(signerName)
                .orElseThrow(SignerNotFoundException::new);
        albumService.deleteSignerAlbums(signerName);
        signerRepository.delete(signer);
    }
}
