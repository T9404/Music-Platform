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
import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.entity.Singer;
import ru.cft.shift.intensive.template.exception.SingerAlreadyExistsException;
import ru.cft.shift.intensive.template.exception.SingerNotFoundException;
import ru.cft.shift.intensive.template.repository.SingerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SingerService;
import ru.cft.shift.intensive.template.service.implementation.SingerServiceImpl;

@SpringBootTest
public class SingerServiceImplTest {

    @MockBean
    private SingerRepository singerRepository;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private SingerService singerService;

    @BeforeEach
    public void setUp() {
        singerService = new SingerServiceImpl(singerRepository, albumService, passwordEncoder);
    }

    @Test
    public void testGetList() {
        List<Singer> singers = new ArrayList<>();
        Singer singer1 = new Singer();
        singer1.setName("My Singer 1");
        Singer singer2 = new Singer();
        singer2.setName("My Singer 2");
        singers.add(singer1);
        singers.add(singer2);
        when(singerRepository.findAll()).thenReturn(singers);

        List<SingerDto> singerDtos = singerService.getList();

        assertEquals(singers.size(), singerDtos.size());
        List<String> singerNames = singers.stream().map(Singer::getName).toList();
        List<String> dtoSingerNames = singerDtos.stream().map(SingerDto::name).toList();
        assertEquals(singerNames, dtoSingerNames);
    }

    @Test
    public void testIsSignerExists_SignerNotExists() {
        String signerName = "My Singer";
        when(singerRepository.findById(signerName)).thenReturn(Optional.empty());

        boolean exists = singerService.isSignerExists(signerName);
        assertFalse(exists);
    }

    @Test
    public void testFindBySignerName_SignerExists() {
        String signerName = "My Singer";
        Singer singer = new Singer();
        singer.setName(signerName);
        when(singerRepository.findById(signerName)).thenReturn(Optional.of(singer));

        SingerDto singerDto = singerService.findBySignerName(signerName);
        assertNotNull(singerDto);
        assertEquals(singer.getName(), singerDto.name());
    }

    @Test
    public void testFindBySignerName_SignerNotFound() {
        String signerName = "Nonexistent Singer";
        when(singerRepository.findById(signerName)).thenReturn(Optional.empty());
        assertThrows(SingerNotFoundException.class, () -> singerService.findBySignerName(signerName));
    }

    @Test
    public void testCreate_SignerDoesNotExist() {
        Singer singer = new Singer();
        singer.setName("My Singer");
        singer.setPassword("myPassword");
        when(singerRepository.findById(singer.getName())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(singer.getPassword())).thenReturn("encodedPassword");

        SingerDto singerDto = singerService.create(singer);
        assertNotNull(singerDto);
        assertEquals(singer.getName(), singerDto.name());
    }

    @Test
    public void testCreate_SignerAlreadyExists() {
        Singer singer = new Singer();
        singer.setName("My Singer");
        when(singerRepository.findById(singer.getName())).thenReturn(Optional.of(new Singer()));
        assertThrows(SingerAlreadyExistsException.class, () -> singerService.create(singer));
    }
}
