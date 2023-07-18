package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.SignerByName;
import ru.cft.shift.intensive.template.mapper.signer.SignerMapper;
import ru.cft.shift.intensive.template.mapper.signer.SignerMapperImpl;
import ru.cft.shift.intensive.template.repository.SignerByNameRepository;
import ru.cft.shift.intensive.template.service.SignerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignerServiceImpl implements SignerService {
    private final SignerByNameRepository signerByNameRepository;
    private final SignerMapper signerMapper = new SignerMapperImpl();

    @Autowired
    public SignerServiceImpl(SignerByNameRepository signerByNameRepository) {
        this.signerByNameRepository = signerByNameRepository;
    }

    @Override
    public List<SignerDto> getList() {
        return signerByNameRepository.findAll().stream()
                .map(signerMapper::entityToSignerDto)
                .collect(Collectors.toList());
    }

    @Override
    public SignerDto findBySignerName(String signerName) {
        return signerByNameRepository.findById(signerName)
                .map(signerMapper::entityToSignerDto)
                .orElseThrow(() -> new IllegalArgumentException("Signer " + signerName + " not found"));
    }

    @Override
    public boolean isExists(String signerName) {
        return signerByNameRepository.findById(signerName).isPresent();
    }

    @Override
    public SignerDto create(SignerByName signer) throws IllegalArgumentException {
        System.out.println(signer.getName());
        /*if (isExists(signer.getName())) {
            throw new IllegalArgumentException("Signer " + signer.getName() + " already exists");
        }*/
        if (signer.getName() != null && signer.getAlbums().size() > 0) {
            Album album = signer.getAlbums().iterator().next();
            System.out.println(album.getName());
            System.out.println(album.getReleaseDate());

        }
        System.out.println(signer.getAlbums());
        signerByNameRepository.save(signer);
        return signerMapper.entityToSignerDto(signer);
    }

    @Override
    public void delete(String signerName) throws Exception {
        SignerByName signer = signerByNameRepository.findById(signerName)
                .orElseThrow(() -> new Exception("Signer " + signerName + " not found"));
        signerByNameRepository.delete(signer);
    }
}
