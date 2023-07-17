package ru.cft.shift.intensive.template.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("signer_by_name")
public class SignerByName {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String name;
    @Column
    private String biography;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Set<Album> albums = new HashSet<>();

    public SignerByName() {
    }

    public SignerByName(String name, String biography, String email, String password, Set<Album> albums) {
        this.name = name;
        this.biography = biography;
        this.email = email;
        this.password = password;
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
