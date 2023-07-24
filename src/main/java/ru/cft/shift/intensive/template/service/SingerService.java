package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.entity.Singer;

import java.util.List;

public interface SingerService {
    List<SingerDto> getList();
    SingerDto findBySignerName(String signerName);
    SingerDto create(Singer singer);
    boolean isSignerExists(String signerName);
    void delete(String signerName);
}
