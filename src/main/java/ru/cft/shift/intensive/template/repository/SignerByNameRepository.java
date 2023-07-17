package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.SignerByName;

public interface SignerByNameRepository extends CassandraRepository<SignerByName, String> {
}
