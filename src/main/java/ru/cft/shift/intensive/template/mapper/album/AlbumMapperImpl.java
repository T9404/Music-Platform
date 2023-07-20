package ru.cft.shift.intensive.template.mapper.album;

import org.springframework.stereotype.Component;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.dto.SongDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;
import ru.cft.shift.intensive.template.entity.Song;

import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AlbumMapperImpl implements AlbumMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public AlbumDto entityToAlbumDto(AlbumByGenre album) {
        return new AlbumDto(album.getAlbumName(), convert(album.getReleaseDate()), album.getSignerName(),
                album.getGenre(), songsToSongsDto(album.getSongs()));
    }

    @Override
    public AlbumDto entityToAlbumDto(AlbumBySinger album) {
        return new AlbumDto(album.getAlbumName(), convert(album.getReleaseDate()), album.getSingerName(),
                album.getGenre(), songsToSongsDto(album.getSongs()));
    }

    @Override
    public AlbumDto entityToAlbumDto(Album album) {
        return new AlbumDto(album.getName(), convert(album.getReleaseDate()), album.getOwner(),
                album.getGenre(), songsToSongsDto(album.getSongs()));
    }

    private Set<SongDto> songsToSongsDto(Set<Song> songs) {
        return songs.stream()
                .map(song -> new SongDto(song.getName(), song.getDuration(), song.getDescription()))
                .collect(Collectors.toSet());
    }

    @Override
    public AlbumByGenre albumToAlbumByGenre(Album album) {
        AlbumByGenre albumByGenre = new AlbumByGenre();
        albumByGenre.setAlbumName(album.getName());
        albumByGenre.setSignerName(album.getName());
        albumByGenre.setGenre(album.getGenre());
        albumByGenre.setSongs(album.getSongs());
        albumByGenre.setReleaseDate(album.getReleaseDate());
        return albumByGenre;
    }

    @Override
    public AlbumBySinger albumToAlbumBySinger(Album album) {
        AlbumBySinger albumBySinger = new AlbumBySinger();
        albumBySinger.setAlbumName(album.getName());
        albumBySinger.setSingerName(album.getName());
        albumBySinger.setGenre(album.getGenre());
        albumBySinger.setSongs(album.getSongs());
        albumBySinger.setReleaseDate(album.getReleaseDate());
        return albumBySinger;
    }

    @Override
    public AlbumByGenre albumBySignerToAlbumByGenre(AlbumBySinger album) {
        AlbumByGenre albumByGenre = new AlbumByGenre();
        albumByGenre.setAlbumName(album.getAlbumName());
        albumByGenre.setSignerName(album.getSingerName());
        albumByGenre.setGenre(album.getGenre());
        albumByGenre.setSongs(album.getSongs());
        albumByGenre.setReleaseDate(album.getReleaseDate());
        return albumByGenre;
    }

    private String convert(Timestamp timestamp) {
        return timestamp.toLocalDateTime().format(FORMATTER);
    }
}
