package ru.cft.shift.intensive.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.shift.intensive.template.dto.UserDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.repository.UsersRepository;
import ru.cft.shift.intensive.template.repository.entity.Users;
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
                .map(user -> new UserDto(user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles()))
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public boolean isExists(String username) {
        return usersRepository.existsById(username);
    }

    @Override
    public void create(Users user) {
        if (isExists(user.getUsername())) {
            throw new IllegalArgumentException("User " + user.getUsername() + " already exists");
        }
        if (user.getRoles().isEmpty()) {
            user.getRoles().add("USER");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        new UsernameDto(user.getUsername());
    }

    @Override
    public void delete(String username) {
        Users users = this.usersRepository.findById(username).orElseThrow(() ->
                new org.springframework.security.core.userdetails.UsernameNotFoundException("User " + username + " not found"));
        this.usersRepository.delete(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = findByUsername(username);
        return User.builder()
                .username(userDto.username())
                .password(userDto.password())
                .roles(userDto.roles().toArray(String[]::new))
                .build();
    }
}
