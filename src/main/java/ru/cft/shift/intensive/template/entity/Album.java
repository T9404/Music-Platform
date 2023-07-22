package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("album")
public class Album {
    private String name;
    private String genre;
    private String owner;
    private Timestamp releaseDate;
    private Set<Song> songs = new HashSet<>();
}
