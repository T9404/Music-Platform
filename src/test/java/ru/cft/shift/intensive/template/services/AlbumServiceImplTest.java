package ru.cft.shift.intensive.template.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;
import ru.cft.shift.intensive.template.entity.Song;
import ru.cft.shift.intensive.template.exception.AlbumAlreadyExistsException;
import ru.cft.shift.intensive.template.repository.AlbumByGenreRepository;
import ru.cft.shift.intensive.template.repository.AlbumBySignerRepository;
import ru.cft.shift.intensive.template.service.AlbumService;
import ru.cft.shift.intensive.template.service.SignerService;
import ru.cft.shift.intensive.template.service.implementation.AlbumServiceImpl;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AlbumServiceImplTest {

    @MockBean
    private AlbumByGenreRepository albumByGenreRepository;

    @MockBean
    private AlbumBySignerRepository albumBySignerRepository;

    @MockBean
    private SignerService signerService;

    private AlbumService albumService;

    @BeforeEach
    public void setUp() {
        albumService = new AlbumServiceImpl(albumByGenreRepository, albumBySignerRepository, signerService);
    }

    @Test
    public void testGetList() {
        // Given
        List<AlbumByGenre> albums = new ArrayList<>();
        AlbumByGenre album1 = createAlbumByGenre("My Album 1", "My Genre 1", "2020-01-01 00:00:00.000000");
        AlbumByGenre album2 = createAlbumByGenre("My Album 2", "My Genre 2", "2020-01-02 00:00:00.000000");
        albums.add(album1);
        albums.add(album2);
        when(albumByGenreRepository.findAll()).thenReturn(albums);

        List<AlbumDto> albumDtos = albumService.getList();

        assertEquals(albums.size(), albumDtos.size());
        List<String> genres = albums.stream().map(AlbumByGenre::getGenre).toList();
        List<String> dtoGenres = albumDtos.stream().map(AlbumDto::genre).toList();
        assertEquals(genres, dtoGenres);
    }

    @Test
    public void testIsAlbumExists_AlbumNotExists() {
        String albumName = "My Album";
        String singerName = "My Singer";

        when(albumBySignerRepository
                .findByKey_SingerNameAndKey_AlbumName(singerName, albumName))
                .thenReturn(Optional.empty());

        boolean exists = albumService.isAlbumExists(albumName, singerName);
        assertFalse(exists);
    }

    @Test
    public void testCreate_AlbumDoesNotExist() {
        Album album = createAlbum("My Album", "My Singer", "My Genre",
                "2020-01-01 00:00:00.000000");
        when(albumBySignerRepository.findByKey_SingerNameAndKey_AlbumName(album.getOwner(),
                album.getName())).thenReturn(Optional.empty());
        when(signerService.isSignerExists(album.getOwner())).thenReturn(false);

        AlbumDto albumDto = albumService.create(album);
        assertNotNull(albumDto);
        assertEquals(album.getName(), albumDto.albumName());
        assertEquals(album.getOwner(), albumDto.signerName());
    }

    @Test
    public void testCreate_AlbumAlreadyExists() {
        Album album = createAlbum("My Album", "My Singer",
                "My Genre", "2020-01-01 00:00:00.000000");
        when(albumBySignerRepository.findByKey_SingerNameAndKey_AlbumName(album.getOwner(),
                album.getName())).thenReturn(Optional.of(new AlbumBySinger()));
        assertThrows(AlbumAlreadyExistsException.class, () -> albumService.create(album));
    }

    private AlbumByGenre createAlbumByGenre(String albumName, String genre, String releaseDate) {
        AlbumByGenre album = new AlbumByGenre();
        album.setAlbumName(albumName);
        album.setGenre(genre);
        album.setReleaseDate(Timestamp.valueOf(releaseDate));
        return album;
    }

    private Album createAlbum(String albumName, String singerName, String genre, String releaseDate) {
        Album album = new Album();
        album.setName(albumName);
        album.setOwner(singerName);
        album.setGenre(genre);
        album.setReleaseDate(Timestamp.valueOf(releaseDate));
        album.setSongs(new HashSet<>(List.of(new Song(UUID.randomUUID(),
                "My Song", "test", singerName, 1))));
        return album;
    }
}
