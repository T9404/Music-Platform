package ru.cft.shift.intensive.template.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cft.shift.intensive.template.dto.UserDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.enumeration.PersonRole;
import ru.cft.shift.intensive.template.exception.UserAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.UserNotFoundException;
import ru.cft.shift.intensive.template.repository.UsersRepository;
import ru.cft.shift.intensive.template.entity.Users;
import ru.cft.shift.intensive.template.service.implementation.UsersServiceImpl;

@SpringBootTest
public class UsersServiceImplTest {

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private UsersServiceImpl usersService;

    @BeforeEach
    public void setUp() {
        usersService = new UsersServiceImpl(usersRepository, passwordEncoder);
    }

    @Test
    public void testList() {
        List<Users> usersList = new ArrayList<>();
        Users user1 = createUser("User1", "password1", "user1@gmail.com", PersonRole.USER);
        Users user2 = createUser("User2", "password2", "user2@gmail.com", PersonRole.ADMIN);
        usersList.add(user1);
        usersList.add(user2);
        when(usersRepository.findAll()).thenReturn(usersList);

        List<UsernameDto> usernames = usersService.list();
        assertEquals(usersList.size(), usernames.size());
        List<String> expectedUsernames = usersList.stream().map(Users::getUsername).toList();
        List<String> actualUsernames = usernames.stream().map(UsernameDto::username).toList();
        assertEquals(expectedUsernames, actualUsernames);
    }

    @Test
    public void testFindByUsername_UserExists() {
        String username = "User1";
        Users user = createUser(username, "password1", "user1@gmail.com", PersonRole.USER);
        when(usersRepository.findById(username)).thenReturn(Optional.of(user));

        UserDto userDto = usersService.findByUsername(username);
        assertNotNull(userDto);
        assertEquals(username, userDto.username());
    }

    @Test
    public void testFindByUsername_UserNotFound() {
        String username = "NonexistentUser";
        when(usersRepository.findById(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> usersService.findByUsername(username));
    }

    @Test
    public void testIsExists_UserExists() {
        String username = "User1";
        when(usersRepository.existsById(username)).thenReturn(true);

        boolean exists = usersService.isExists(username);
        assertTrue(exists);
    }

    @Test
    public void testIsExists_UserNotExists() {
        String username = "NonexistentUser";
        when(usersRepository.existsById(username)).thenReturn(false);

        boolean exists = usersService.isExists(username);
        assertFalse(exists);
    }

    @Test
    public void testCreate_UserDoesNotExist() {
        Users user = createUser("User1", "password1", "user1@gmail.com", PersonRole.USER);
        when(usersRepository.existsById(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        UsernameDto usernameDto = usersService.create(user);
        assertNotNull(usernameDto);
        assertEquals(user.getUsername(), usernameDto.username());
    }

    @Test
    public void testCreate_UserAlreadyExists() {
        Users user = createUser("User1", "password1", "user1@gmail.com", PersonRole.USER);
        when(usersRepository.existsById(user.getUsername())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> usersService.create(user));
    }

    @Test
    public void testDelete_UserExists() {
        String username = "User1";
        Users user = createUser(username, "password1", "user1@gmail.com", PersonRole.USER);
        when(usersRepository.findById(username)).thenReturn(Optional.of(user));

        usersService.delete(username);
        verify(usersRepository, times(1)).delete(user);
    }

    @Test
    public void testDelete_UserNotFound() {
        String username = "NonexistentUser";
        when(usersRepository.findById(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> usersService.delete(username));
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        String username = "User1";
        Users user = createUser(username, "password1", "user1@gmail.com", PersonRole.USER);
        when(usersRepository.findById(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = usersService.loadUserByUsername(username);
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    private Users createUser(String username, String password, String email, PersonRole role) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        return user;
    }
}
