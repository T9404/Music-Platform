package ru.cft.shift.intensive.template.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.HashSet;
import java.util.Set;

@UserDefinedType("album")
public class Album {
    private String name;
    private String genre;
    private String owner;
    private String releaseDate;
    private Set<Song> songs = new HashSet<>();

    public Album() {
    }

    public Album(String name, String genre, String owner, String releaseDate, Set<Song> songs) {
        this.name = name;
        this.genre = genre;
        this.owner = owner;
        this.releaseDate = releaseDate;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
