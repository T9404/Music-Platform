package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("signer_by_name")
public class Signer {
    @PrimaryKey
    private String name;
    @Column
    private String biography;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Set<Album> albums = new HashSet<>();
}
