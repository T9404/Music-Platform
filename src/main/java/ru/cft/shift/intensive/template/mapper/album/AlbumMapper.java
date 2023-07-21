package ru.cft.shift.intensive.template.mapper.album;

import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.AlbumByGenre;
import ru.cft.shift.intensive.template.entity.AlbumBySinger;

public interface AlbumMapper {
    AlbumDto entityToAlbumDto(AlbumByGenre album);
    AlbumDto entityToAlbumDto(AlbumBySinger album);
    AlbumDto entityToAlbumDto(Album album);
    AlbumByGenre albumToAlbumByGenre(Album album);
    AlbumBySinger albumToAlbumBySinger(Album album);
    AlbumByGenre albumBySignerToAlbumByGenre(AlbumBySinger album);
}
