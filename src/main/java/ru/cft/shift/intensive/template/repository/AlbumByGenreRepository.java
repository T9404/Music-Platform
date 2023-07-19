package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.Song;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AlbumByGenreRepository extends CassandraRepository<AlbumByGenre, AlbumByGenre.Key> {
    List<AlbumByGenre> findByKey_Genre(String genre);

    Optional<AlbumByGenre> findByAlbumNameAndSignerName(String signerName, String albumName);

    Optional<List<AlbumByGenre>> findAllByKey_Genre(String genre);
}
