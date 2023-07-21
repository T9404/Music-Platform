package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("song")
public class Song {
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id = Uuids.timeBased();
    private String name;
    private String owner;
    private String description;
    private int duration;
}
