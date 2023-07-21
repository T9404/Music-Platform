package ru.cft.shift.intensive.template.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.UserDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.enumeration.PersonRole;
import ru.cft.shift.intensive.template.exception.UserAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.UserNotFoundException;
import ru.cft.shift.intensive.template.repository.UsersRepository;
import ru.cft.shift.intensive.template.entity.Users;
import ru.cft.shift.intensive.template.service.UsersService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UsernameDto> list() {
        return this.usersRepository.findAll().stream().map(users -> new UsernameDto(users.getUsername())).toList();
    }

    public UserDto findByUsername(String username) {
        return this.usersRepository.findById(username)
                .map(user -> new UserDto(user.getUsername(), user.getPassword(), user.getEmail(), user.getRole()))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public boolean isExists(String username) {
        return usersRepository.existsById(username);
    }

    @Override
    public UsernameDto create(Users user) {
        checkUserNotExists(user.getUsername());
        setDefaultRole(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return new UsernameDto(user.getUsername());
    }

    private void checkUserNotExists(String username) {
        if (isExists(username)) {
            throw new UserAlreadyExistsException();
        }
    }

    private void setDefaultRole(Users user) {
        if (user.getRole() == null) {
            user.setRole(PersonRole.USER);
        }
    }

    @Override
    public void delete(String username) {
        Users users = this.usersRepository.findById(username).orElseThrow(UserNotFoundException::new);
        this.usersRepository.delete(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = findByUsername(username);
        return User.builder()
                .username(userDto.username())
                .password(userDto.password())
                .roles(userDto.role())
                .build();
    }
}
