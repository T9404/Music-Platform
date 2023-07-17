package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.SignerByName;
import ru.cft.shift.intensive.template.repository.SignerByNameRepository;
import ru.cft.shift.intensive.template.service.SignerService;

import java.util.List;

@Service
public class SignerServiceImpl implements SignerService {
    private final SignerByNameRepository signerByNameRepository;

    @Autowired
    public SignerServiceImpl(SignerByNameRepository signerByNameRepository) {
        this.signerByNameRepository = signerByNameRepository;
    }

    @Override
    public List<SignerDto> getList() {
        return null;
    }

    @Override
    public SignerDto findBySignerName(String signerName) {
        return null;
    }

    @Override
    public boolean isExists(String signerName) {
        return false;
    }

    @Override
    public SignerDto create(SignerByName signer) {
        return null;
    }

    @Override
    public void delete(String signerName) {

    }
}
