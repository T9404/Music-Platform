package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Signer;
import ru.cft.shift.intensive.template.exception.SignerAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.SignerNotFoundException;
import ru.cft.shift.intensive.template.mapper.signer.SignerMapper;
import ru.cft.shift.intensive.template.mapper.signer.SignerMapperImpl;
import ru.cft.shift.intensive.template.repository.SignerByNameRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SignerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignerServiceImpl implements SignerService {
    private final SignerByNameRepository signerByNameRepository;
    private final AlbumService albumService;
    private final SignerMapper signerMapper = new SignerMapperImpl();

    @Autowired
    public SignerServiceImpl(SignerByNameRepository signerByNameRepository, @Lazy AlbumService albumService) {
        this.signerByNameRepository = signerByNameRepository;
        this.albumService = albumService;
    }

    @Override
    public List<SignerDto> getList() {
        return signerByNameRepository.findAll().stream()
                .map(signerMapper::entityToSignerDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSignerExists(String signerName) {
        return signerByNameRepository.findById(signerName).isPresent();
    }

    @Override
    public SignerDto findBySignerName(String signerName) {
        return signerByNameRepository.findById(signerName)
                .map(signerMapper::entityToSignerDto)
                .orElseThrow(() -> new IllegalArgumentException("Signer " + signerName + " not found"));
    }
    @Override
    public SignerDto create(Signer signer) {
        if (isSignerExists(signer.getName())) {
            throw new SignerAlreadyExistsException();
        }
        signerByNameRepository.save(signer);
        updateAlbums(signer);
        return signerMapper.entityToSignerDto(signer);
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
        Signer signer = signerByNameRepository.findById(signerName)
                .orElseThrow(SignerNotFoundException::new);
        albumService.deleteSignerAlbums(signerName);
        signerByNameRepository.delete(signer);
    }
}
