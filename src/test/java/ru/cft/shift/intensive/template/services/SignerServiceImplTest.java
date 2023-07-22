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
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.Signer;
import ru.cft.shift.intensive.template.exception.SignerAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.SignerNotFoundException;
import ru.cft.shift.intensive.template.repository.SignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SignerService;
import ru.cft.shift.intensive.template.service.implementation.SignerServiceImpl;

@SpringBootTest
public class SignerServiceImplTest {

    @MockBean
    private SignerRepository signerRepository;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private SignerService signerService;

    @BeforeEach
    public void setUp() {
        signerService = new SignerServiceImpl(signerRepository, albumService, passwordEncoder);
    }

    @Test
    public void testGetList() {
        List<Signer> signers = new ArrayList<>();
        Signer signer1 = new Signer();
        signer1.setName("My Singer 1");
        Signer signer2 = new Signer();
        signer2.setName("My Singer 2");
        signers.add(signer1);
        signers.add(signer2);
        when(signerRepository.findAll()).thenReturn(signers);

        List<SignerDto> signerDtos = signerService.getList();

        assertEquals(signers.size(), signerDtos.size());
        List<String> singerNames = signers.stream().map(Signer::getName).toList();
        List<String> dtoSingerNames = signerDtos.stream().map(SignerDto::name).toList();
        assertEquals(singerNames, dtoSingerNames);
    }

    @Test
    public void testIsSignerExists_SignerNotExists() {
        String signerName = "My Singer";
        when(signerRepository.findById(signerName)).thenReturn(Optional.empty());

        boolean exists = signerService.isSignerExists(signerName);
        assertFalse(exists);
    }

    @Test
    public void testFindBySignerName_SignerExists() {
        String signerName = "My Singer";
        Signer signer = new Signer();
        signer.setName(signerName);
        when(signerRepository.findById(signerName)).thenReturn(Optional.of(signer));

        SignerDto signerDto = signerService.findBySignerName(signerName);
        assertNotNull(signerDto);
        assertEquals(signer.getName(), signerDto.name());
    }

    @Test
    public void testFindBySignerName_SignerNotFound() {
        String signerName = "Nonexistent Singer";
        when(signerRepository.findById(signerName)).thenReturn(Optional.empty());
        assertThrows(SignerNotFoundException.class, () -> signerService.findBySignerName(signerName));
    }

    @Test
    public void testCreate_SignerDoesNotExist() {
        Signer signer = new Signer();
        signer.setName("My Singer");
        signer.setPassword("myPassword");
        when(signerRepository.findById(signer.getName())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signer.getPassword())).thenReturn("encodedPassword");

        SignerDto signerDto = signerService.create(signer);
        assertNotNull(signerDto);
        assertEquals(signer.getName(), signerDto.name());
    }

    @Test
    public void testCreate_SignerAlreadyExists() {
        Signer signer = new Signer();
        signer.setName("My Singer");
        when(signerRepository.findById(signer.getName())).thenReturn(Optional.of(new Signer()));
        assertThrows(SignerAlreadyExistsException.class, () -> signerService.create(signer));
    }
}
