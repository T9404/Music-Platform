package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;

import java.util.List;
import java.util.Optional;

public interface AlbumByGenreRepository extends CassandraRepository<AlbumByGenre, AlbumByGenre.Key> {
    Optional<List<AlbumByGenre>> findAllByKey_Genre(String genre);
}
