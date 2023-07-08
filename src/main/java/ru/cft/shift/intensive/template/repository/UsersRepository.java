package ru.cft.shift.intensive.template.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shift.intensive.template.repository.entity.Users;

@Repository
public interface UsersRepository extends CassandraRepository<Users, String> {
}
