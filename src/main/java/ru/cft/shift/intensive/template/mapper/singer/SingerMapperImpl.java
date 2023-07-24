package ru.cft.shift.intensive.template.mapper.singer;

import org.springframework.stereotype.Component;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Singer;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapper;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapperImpl;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SingerMapperImpl implements SingerMapper {
    private final AlbumMapper albumMapper = new AlbumMapperImpl();

    @Override
    public SingerDto entityToSignerDto(Singer singer) {
        return new SingerDto(singer.getName(), singer.getEmail(),
                singer.getBiography(), albumsToAlbumsDto(singer.getAlbums()));
    }

    private Set<AlbumDto> albumsToAlbumsDto(Set<Album> albums) {
        return albums.stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toSet());
    }
}
