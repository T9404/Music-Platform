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

@Getter
@Setter
@NoArgsConstructor
@Table("album_by_genre")
public class AlbumByGenre implements Albums {
    @PrimaryKey
    private Key key = new Key();
    @Column("album_name")
    private String albumName;
    @Column("release_date")
    private Timestamp releaseDate;
    @Column("songs")
    private Set<Song> songs = new HashSet<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class Key {
        @PrimaryKeyColumn(name = "genre", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String genre;
        @PrimaryKeyColumn(name = "singer_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private String singerName;

        public Key(String genre) {
            this.genre = genre;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Key key))  {
                return false;
            }
            return Objects.equals(genre, key.genre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(genre);
        }
    }

    public AlbumByGenre(String genre, String singerName, String albumName, Timestamp releaseDate, Set<Song> songs) {
        key.genre = genre;
        key.singerName = singerName;
        this.albumName = albumName;
        this.releaseDate = releaseDate;
        this.songs = songs;
    }

    public void setGenre(String genre) {
        key.genre = genre;
    }

    public String getGenre() {
        return key.genre;
    }

    public void setSignerName(String signerName) {
        key.singerName = signerName;
    }

    public String getSignerName() {
        return key.singerName;
    }
}
