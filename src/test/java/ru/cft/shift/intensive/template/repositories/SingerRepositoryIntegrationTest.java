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
import ru.cft.shift.intensive.template.entity.Singer;
import ru.cft.shift.intensive.template.repository.SingerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataCassandraTest
@Import(TestConfig.class)
public class SingerRepositoryIntegrationTest {
    @Autowired
    private SingerRepository singerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("cassandra.native.epoll.enabled", "false");
    }

    @AfterEach
    public void tearDown() {
        singerRepository.deleteAll();
    }

    @Test
    public void testSaveSigner() {
        Singer singer = new Singer();
        singer.setName("test");
        singer.setPassword(passwordEncoder.encode("test"));
        singer.setEmail("test@gmail.com");
        singer.setBiography("test");
        singer.setAlbums(null);
        singerRepository.save(singer);

        Singer savedSinger = singerRepository.save(singer);
        Singer retrievedSinger = singerRepository.findById(savedSinger.getName()).orElse(null);

        assertNotNull(retrievedSinger);
        assertEquals("test", retrievedSinger.getName());
        assertNotEquals(passwordEncoder.encode("test"), retrievedSinger.getPassword());
        assertEquals("test@gmail.com", retrievedSinger.getEmail());
        assertEquals("test", retrievedSinger.getBiography());
        assertEquals(savedSinger.getName(), retrievedSinger.getName());
    }

    @Test
    public void testGetSigner() {
        Singer singer = new Singer();
        singer.setName("test");
        singer.setPassword(passwordEncoder.encode("test"));
        singer.setEmail("test@gmail.com");
        singer.setBiography("test");
        singer.setAlbums(null);
        singerRepository.save(singer);

        Optional<Singer> retrievedSignerOptional = singerRepository.findById("test");
        Singer retrievedSinger = retrievedSignerOptional.orElseThrow(() ->
                new AssertionError("Signer not found"));

        assertEquals("test", retrievedSinger.getName());
        assertNotEquals(passwordEncoder.encode("test"), retrievedSinger.getPassword());
        assertEquals("test@gmail.com", retrievedSinger.getEmail());
        assertEquals("test", retrievedSinger.getBiography());
    }

    @Test
    public void testDeleteSigner() {
        Singer singer = new Singer();
        singer.setName("test");
        singer.setPassword(passwordEncoder.encode("test"));
        singer.setEmail("test@gmail.com");
        singer.setBiography("test");
        singer.setAlbums(null);
        singerRepository.save(singer);
        singerRepository.deleteById("test");

        Optional<Singer> deletedSignerOptional = singerRepository.findById("test");
        assertFalse(deletedSignerOptional.isPresent(), "Signer should be deleted");
    }
}
