package ru.cft.shift.intensive.template.mapper.signer;

import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.SignerByName;

public interface SignerMapper {
    SignerDto entityToSignerDto(SignerByName signerByName);
}