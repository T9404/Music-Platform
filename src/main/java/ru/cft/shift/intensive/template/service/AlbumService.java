package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Song;

import java.util.List;

public interface AlbumService {
    List<AlbumDto> getList();
    AlbumDto create(Album album);
    boolean isAlbumExists(String albumName, String singerName);
    void deleteAlbum(String albumName, String singerName);

    List<AlbumDto> getSignerAlbums(String singerName);
    void deleteSignerAlbums(String singerName);
    List<AlbumDto> getAlbumsByGenre(String genre);

    AlbumDto addSong(String albumName, Song song);
    void deleteSong(String albumName, String ownerName, String songName);
}
