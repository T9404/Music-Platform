package ru.cft.shift.intensive.template.mapper.singer;

import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.entity.Singer;

public interface SingerMapper {
    SingerDto entityToSignerDto(Singer singer);
}
