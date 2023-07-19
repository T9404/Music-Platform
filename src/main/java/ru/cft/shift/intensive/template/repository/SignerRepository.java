package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import ru.cft.shift.intensive.template.entity.Signer;

public interface SignerRepository extends CassandraRepository<Signer, String> {
}
