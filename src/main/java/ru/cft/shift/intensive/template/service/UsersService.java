package ru.cft.shift.intensive.template.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.cft.shift.intensive.template.dto.UserDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.exception.UsernameNotFoundException;

import java.util.List;

public interface UsersService {
    List<UsernameDto> list();

    UserDto findByUsername(String username);

    UsernameDto create(UserDto user);

    void delete(String username);
}
