package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.Signer;

public interface SignerByNameRepository extends CassandraRepository<Signer, String> {
}
