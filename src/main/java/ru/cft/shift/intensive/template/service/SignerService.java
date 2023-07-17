package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.SignerByName;

import java.util.List;

public interface SignerService {
    List<SignerDto> getList();
    SignerDto findBySignerName(String signerName);
    boolean isExists(String signerName);
    SignerDto create(SignerByName signer);
    void delete(String signerName);
}
