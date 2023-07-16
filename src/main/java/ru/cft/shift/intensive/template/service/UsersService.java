package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.UserDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.repository.entity.Users;

import java.util.List;

public interface UsersService {
    List<UsernameDto> list();

    UserDto findByUsername(String username);
    boolean isExists(String username);

    void create(Users user);

    void delete(String username);
}
