package ru.cft.shift.intensive.template.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
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

    @NoArgsConstructor
    @AllArgsConstructor
    @PrimaryKeyClass
    public static class Key {
        @PrimaryKeyColumn(name = "signer_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        private String singerName;
        @PrimaryKeyColumn(name = "album_name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        private String albumName;

        public Key(String singerName) {
            this.singerName = singerName;
        }
    }

    public AlbumBySinger(String singerName, String albumName, String genre, String releaseDate, Set<Song> songs) {
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
