package ru.cft.shift.intensive.template.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;
import ru.cft.shift.intensive.template.repository.AlbumByGenreRepository;
import ru.cft.shift.intensive.template.repository.AlbumBySignerRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataCassandraTest
public class AlbumRepositoryIntegrationTest {
    @Autowired
    private AlbumBySignerRepository albumBySignerRepository;
    @Autowired
    private AlbumByGenreRepository albumByGenreRepository;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("cassandra.native.epoll.enabled", "false");
    }

    @AfterEach
    public void tearDown() {
        albumBySignerRepository.deleteAll();
    }

    @Test
    public void testFindAllAlbumsBySigner() {
        AlbumBySinger album1 = new AlbumBySinger();
        album1.setSingerName("test");
        album1.setGenre("test");
        album1.setAlbumName("album1");
        album1.setReleaseDate(Timestamp.valueOf("2020-01-01 00:00:00.000000"));
        album1.setSongs(null);
        albumBySignerRepository.save(album1);

        AlbumBySinger album2 = new AlbumBySinger();
        album2.setSingerName("test");
        album2.setGenre("test");
        album2.setAlbumName("album2");
        album2.setReleaseDate(Timestamp.valueOf("2020-01-02 00:00:00.000000"));
        album2.setSongs(null);
        albumBySignerRepository.save(album2);

        Optional<List<AlbumBySinger>> allAlbumsBySigner = albumBySignerRepository.findAllByKey_SingerName("test");
        assertTrue(allAlbumsBySigner.isPresent());
        assertEquals(2, allAlbumsBySigner.get().size());
        assertEquals("album1", allAlbumsBySigner.get().get(0).getAlbumName());
        assertEquals("album2", allAlbumsBySigner.get().get(1).getAlbumName());
    }

    @Test
    public void testSaveAlbumBySigner() {
        AlbumBySinger album = new AlbumBySinger();
        album.setSingerName("test");
        album.setGenre("test");
        album.setAlbumName("test");
        album.setReleaseDate(Timestamp.valueOf("2020-01-01 00:00:00.000000"));
        album.setSongs(null);

        AlbumBySinger savedAlbum = albumBySignerRepository.save(album);
        AlbumBySinger retrievedAlbum = albumBySignerRepository.findById(savedAlbum.getKey()).orElse(null);

        assertNotNull(retrievedAlbum);
        assertEquals("test", retrievedAlbum.getSingerName());
        assertEquals("test", retrievedAlbum.getGenre());
        assertEquals("test", retrievedAlbum.getAlbumName());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00.000000"), retrievedAlbum.getReleaseDate());
        assertEquals(savedAlbum.getKey(), retrievedAlbum.getKey());
    }

    @Test
    public void testDeleteAlbumBySigner() {
        AlbumBySinger album = new AlbumBySinger();
        album.setSingerName("test");
        album.setGenre("test");
        album.setAlbumName("test");
        album.setReleaseDate(Timestamp.valueOf("2020-01-01 00:00:00.000000"));
        album.setSongs(null);
        AlbumBySinger savedAlbum = albumBySignerRepository.save(album);

        albumBySignerRepository.deleteById(savedAlbum.getKey());
        Optional<AlbumBySinger> retrievedAlbum = albumBySignerRepository.findById(savedAlbum.getKey());
        assertFalse(retrievedAlbum.isPresent());
    }

    @Test
    public void testFindAllAlbumsByGenre() {
        AlbumByGenre album1 = new AlbumByGenre();
        album1.setGenre("example");
        album1.setSignerName("signer1");
        album1.setAlbumName("album1");
        album1.setReleaseDate(Timestamp.valueOf("2020-01-01 00:00:00.000000"));
        album1.setSongs(null);
        albumByGenreRepository.save(album1);

        AlbumByGenre album2 = new AlbumByGenre();
        album2.setGenre("example");
        album2.setSignerName("signer2");
        album2.setAlbumName("album2");
        album2.setReleaseDate(Timestamp.valueOf("2020-01-02 00:00:00.000000"));
        album2.setSongs(null);
        albumByGenreRepository.save(album2);

        Optional<List<AlbumByGenre>> allAlbumsByGenre = Optional.ofNullable(albumByGenreRepository.findAllByKey_Genre("example"));
        assertTrue(allAlbumsByGenre.isPresent());
        assertEquals("album2", allAlbumsByGenre.get().get(0).getAlbumName());
        assertEquals("album1", allAlbumsByGenre.get().get(1).getAlbumName());
    }

    @Test
    public void testSaveAlbumByGenre() {
        AlbumByGenre album = new AlbumByGenre();
        album.setSignerName("test");
        album.setGenre("test");
        album.setAlbumName("test");
        album.setReleaseDate(Timestamp.valueOf("2020-01-01 00:00:00.000000"));
        album.setSongs(null);

        AlbumByGenre savedAlbum = albumByGenreRepository.save(album);
        AlbumByGenre retrievedAlbum = albumByGenreRepository.findById(savedAlbum.getKey()).orElse(null);

        assertNotNull(retrievedAlbum);
        assertEquals("test", retrievedAlbum.getGenre());
        assertEquals("test", retrievedAlbum.getAlbumName());
        assertEquals(Timestamp.valueOf("2020-01-01 00:00:00.000000"), retrievedAlbum.getReleaseDate());
        assertEquals(savedAlbum.getKey(), retrievedAlbum.getKey());
    }

    @Test
    public void testDeleteAlbumByGenre() {
        AlbumByGenre album = new AlbumByGenre();
        album.setGenre("test");
        album.setSignerName("test");
        album.setAlbumName("test");
        album.setReleaseDate(Timestamp.valueOf("2020-01-01 00:00:00.000000"));
        album.setSongs(null);
        AlbumByGenre savedAlbum = albumByGenreRepository.save(album);

        albumByGenreRepository.deleteById(savedAlbum.getKey());
        Optional<AlbumByGenre> retrievedAlbum = albumByGenreRepository.findById(savedAlbum.getKey());
        assertFalse(retrievedAlbum.isPresent());
    }
}
