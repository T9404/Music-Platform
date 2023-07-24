package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.Singer;

public interface SingerRepository extends CassandraRepository<Singer, String> {
}
