package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.UserDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.entity.Users;

import java.util.List;

public interface UsersService {
    List<UsernameDto> list();
    UserDto findByUsername(String username);
    boolean isExists(String username);
    UsernameDto create(Users user);
    void delete(String username);
}
