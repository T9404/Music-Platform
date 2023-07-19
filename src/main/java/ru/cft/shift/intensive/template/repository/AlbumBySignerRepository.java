package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;

import java.util.List;
import java.util.Optional;

public interface AlbumBySignerRepository extends CassandraRepository<AlbumBySinger, AlbumBySinger.Key> {
    Optional<AlbumBySinger> findByKey_SingerNameAndKey_AlbumName(String singerName, String albumName);
    Optional<List<AlbumBySinger>> findAllByKey_SingerName(String singerName);
}
