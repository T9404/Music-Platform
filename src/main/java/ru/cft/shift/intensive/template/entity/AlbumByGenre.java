package ru.cft.shift.intensive.template.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.HashSet;
import java.util.Set;

@Table("album_by_genre")
public class AlbumByGenre implements Albums {
    @PrimaryKey
    private Key key = new Key();
    @Column("album_name")
    private String albumName;
    @Column("release_date")
    private String releaseDate;
    @Column("songs")
    private Set<Song> songs = new HashSet<>();

    @PrimaryKeyClass
    public static class Key {
        @PrimaryKeyColumn(name = "genre", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String genre;
        @PrimaryKeyColumn(name = "signer_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private String signerName;

        public Key() {
        }

        public Key(String genre) {
            this.genre = genre;
        }
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public void setGenre(String genre) {
        key.genre = genre;
    }

    public String getGenre() {
        return key.genre;
    }

    public void setSignerName(String signerName) {
        key.signerName = signerName;
    }

    public String getSignerName() {
        return key.signerName;
    }
}
