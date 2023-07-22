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
import ru.cft.shift.intensive.template.entity.Signer;
import ru.cft.shift.intensive.template.repository.SignerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataCassandraTest
@Import(TestConfig.class)
public class SignerRepositoryIntegrationTest {
    @Autowired
    private SignerRepository signerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("cassandra.native.epoll.enabled", "false");
    }

    @AfterEach
    public void tearDown() {
        signerRepository.deleteAll();
    }

    @Test
    public void testSaveSigner() {
        Signer signer = new Signer();
        signer.setName("test");
        signer.setPassword(passwordEncoder.encode("test"));
        signer.setEmail("test@gmail.com");
        signer.setBiography("test");
        signer.setAlbums(null);
        signerRepository.save(signer);

        Signer savedSigner = signerRepository.save(signer);
        Signer retrievedSigner = signerRepository.findById(savedSigner.getName()).orElse(null);

        assertNotNull(retrievedSigner);
        assertEquals("test", retrievedSigner.getName());
        assertNotEquals(passwordEncoder.encode("test"), retrievedSigner.getPassword());
        assertEquals("test@gmail.com", retrievedSigner.getEmail());
        assertEquals("test", retrievedSigner.getBiography());
        assertEquals(savedSigner.getName(), retrievedSigner.getName());
    }

    @Test
    public void testGetSigner() {
        Signer signer = new Signer();
        signer.setName("test");
        signer.setPassword(passwordEncoder.encode("test"));
        signer.setEmail("test@gmail.com");
        signer.setBiography("test");
        signer.setAlbums(null);
        signerRepository.save(signer);

        Optional<Signer> retrievedSignerOptional = signerRepository.findById("test");
        Signer retrievedSigner = retrievedSignerOptional.orElseThrow(() ->
                new AssertionError("Signer not found"));

        assertEquals("test", retrievedSigner.getName());
        assertNotEquals(passwordEncoder.encode("test"), retrievedSigner.getPassword());
        assertEquals("test@gmail.com", retrievedSigner.getEmail());
        assertEquals("test", retrievedSigner.getBiography());
    }

    @Test
    public void testDeleteSigner() {
        Signer signer = new Signer();
        signer.setName("test");
        signer.setPassword(passwordEncoder.encode("test"));
        signer.setEmail("test@gmail.com");
        signer.setBiography("test");
        signer.setAlbums(null);
        signerRepository.save(signer);
        signerRepository.deleteById("test");

        Optional<Signer> deletedSignerOptional = signerRepository.findById("test");
        assertFalse(deletedSignerOptional.isPresent(), "Signer should be deleted");
    }
}
