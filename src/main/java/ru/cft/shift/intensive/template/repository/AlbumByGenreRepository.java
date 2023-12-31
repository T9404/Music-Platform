package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;

import java.util.List;

public interface AlbumByGenreRepository extends CassandraRepository<AlbumByGenre, AlbumByGenre.Key> {
    List<AlbumByGenre> findAllByKey_Genre(String genre);
}
