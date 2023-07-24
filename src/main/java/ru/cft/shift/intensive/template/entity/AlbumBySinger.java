package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Table("album_by_singer")
public class AlbumBySinger implements Albums {
    @PrimaryKey
    private Key key = new Key();
    @Column
    private String genre;
    @Column("release_date")
    private Timestamp releaseDate;
    @Column("songs")
    private Set<Song> songs = new HashSet<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class Key {
        @PrimaryKeyColumn(name = "singer_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String singerName;
        @PrimaryKeyColumn(name = "album_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private String albumName;

        public Key(String singerName) {
            this.singerName = singerName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Key key)) {
                return false;
            }
            return Objects.equals(singerName, key.singerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(singerName);
        }
    }

    public AlbumBySinger(String singerName, String albumName, String genre, Timestamp releaseDate, Set<Song> songs) {
        this.key.singerName = singerName;
        this.key.albumName = albumName;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.songs = songs;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
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
