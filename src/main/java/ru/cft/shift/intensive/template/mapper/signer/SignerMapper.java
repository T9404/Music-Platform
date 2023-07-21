package ru.cft.shift.intensive.template.mapper.signer;

import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.Signer;

public interface SignerMapper {
    SignerDto entityToSignerDto(Signer signer);
}
