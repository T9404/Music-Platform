package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.dto.SongDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Song;

import java.util.List;

public interface AlbumService {
    List<AlbumDto> getList();
    AlbumDto getAlbum(String albumName, String singerName);
    AlbumDto create(Album album);
    boolean isAlbumExists(String albumName, String singerName);
    void delete(String albumName, String singerName);
    List<AlbumDto> getSignerAlbums(String singerName);
    void deleteSignerAlbums(String singerName);
    AlbumDto addSong(String albumName, Song song);
    void deleteSong(String albumName, String ownerName, String songName);
    List<AlbumDto> getAlbumsByGenre(String genre);
}
