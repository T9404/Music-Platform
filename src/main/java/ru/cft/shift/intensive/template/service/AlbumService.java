package ru.cft.shift.intensive.template.service;

import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;

import java.util.List;

public interface AlbumService {
    List<AlbumDto> getList();
    AlbumDto getAlbum(String albumName, String singerName);
    boolean isExists(String albumName, String singerName);
    AlbumDto create(Album album);
    void delete(String albumName, String singerName);
}
