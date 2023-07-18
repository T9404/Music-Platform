package ru.cft.shift.intensive.template.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.HashSet;
import java.util.Set;

@Table("album_by_signer")
public class AlbumBySinger implements Albums {
    @PrimaryKey
    private Key key = new Key();
    @Column
    private String genre;
    @Column("release_date")
    private String releaseDate;
    @Column("songs")
    private Set<Song> songs = new HashSet<>();

    @PrimaryKeyClass
    public static class Key {
        @PrimaryKeyColumn(name = "signer_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String singerName;
        @PrimaryKeyColumn(name = "album_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private String albumName;

        public Key() {
        }

        public Key(String singerName) {
            this.singerName = singerName;
        }
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public String getSingerName() {
        return key.singerName;
    }

    public void setSingerName(String singerName) {
        key.singerName = singerName;
    }

    public String getAlbumName() {
        return key.albumName;
    }

    public void setAlbumName(String albumName) {
        key.albumName = albumName;
    }
}
