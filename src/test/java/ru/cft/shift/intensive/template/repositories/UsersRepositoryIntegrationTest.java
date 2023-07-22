package ru.cft.shift.intensive.template.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shift.intensive.template.config.TestConfig;
import ru.cft.shift.intensive.template.entity.Users;
import ru.cft.shift.intensive.template.repository.UsersRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataCassandraTest
@Import(TestConfig.class)
public class UsersRepositoryIntegrationTest {

    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("cassandra.native.epoll.enabled", "false");
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        Users user = new Users();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@gmail.com");
        userRepository.save(user);
        Optional<Users> savedUserOptional  = userRepository.findById("test");
        Users savedUser = savedUserOptional.orElseThrow(() -> new AssertionError("User not found"));
        assertEquals("test", savedUser.getUsername());
        assertNotEquals(passwordEncoder.encode("test"), savedUser.getPassword());
        assertEquals("test@gmail.com", savedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        Users user = new Users();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@gmail.com");
        userRepository.save(user);
        userRepository.deleteById("test");

        Optional<Users> deletedUserOptional = userRepository.findById("test");
        assertFalse(deletedUserOptional.isPresent(), "User should be deleted");
    }

    @Test
    public void testGetUser() {
        Users user = new Users();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@gmail.com");
        userRepository.save(user);

        Optional<Users> retrievedUserOptional = userRepository.findById("test");
        Users retrievedUser = retrievedUserOptional.orElseThrow(() -> new AssertionError("User not found"));

        assertEquals("test", retrievedUser.getUsername());
        assertNotEquals(passwordEncoder.encode("test"), retrievedUser.getPassword());
        assertEquals("test@gmail.com", retrievedUser.getEmail());
    }
}
