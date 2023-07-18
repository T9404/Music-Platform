package ru.cft.shift.intensive.template.mapper.signer;

import org.springframework.stereotype.Component;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.SignerByName;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapper;
import ru.cft.shift.intensive.template.mapper.album.AlbumMapperImpl;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SignerMapperImpl implements SignerMapper {
    private final AlbumMapper albumMapper = new AlbumMapperImpl();

    @Override
    public SignerDto entityToSignerDto(SignerByName signerByName) {
        return new SignerDto(signerByName.getName(), signerByName.getEmail(),
                signerByName.getBiography(), albumsToAlbumsDto(signerByName.getAlbums()));
    }

    private Set<AlbumDto> albumsToAlbumsDto(Set<Album> albums) {
        return albums.stream()
                .map(albumMapper::entityToAlbumDto)
                .collect(Collectors.toSet());
    }

}
