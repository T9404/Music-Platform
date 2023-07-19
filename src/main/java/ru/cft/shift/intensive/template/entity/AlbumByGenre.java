package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.HashSet;
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
    private String releaseDate;
    @Column("songs")
    private Set<Song> songs = new HashSet<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class Key {
        @PrimaryKeyColumn(name = "genre", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String genre;
        @PrimaryKeyColumn(name = "signer_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private String signerName;

        public Key(String genre) {
            this.genre = genre;
        }
    }

    public AlbumByGenre(String genre, String signerName, String albumName, String releaseDate, Set<Song> songs) {
        key.genre = genre;
        key.signerName = signerName;
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
        key.signerName = signerName;
    }

    public String getSignerName() {
        return key.signerName;
    }
}
