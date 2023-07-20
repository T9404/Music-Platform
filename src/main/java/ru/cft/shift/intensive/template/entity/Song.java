package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType("song")
public class Song {
    private int id;
    private String name;
    private String owner;
    private String description;
    private int duration;
}
