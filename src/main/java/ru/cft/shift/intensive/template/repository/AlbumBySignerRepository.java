package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;

import java.util.List;
import java.util.Optional;

public interface AlbumBySignerRepository extends CassandraRepository<AlbumBySinger, AlbumBySinger.Key> {
    List<AlbumBySinger> findByKey_SingerName(String singerName);
    Optional<AlbumBySinger> findByKey_SingerNameAndKey_AlbumName(String singerName, String albumName);
}
