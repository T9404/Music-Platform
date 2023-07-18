package ru.cft.shift.intensive.template.controller;

import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.service.AlbumService;

import java.util.List;

@RestController
@Validated
@RequestMapping("albums")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping()
    public ResponseEntity<List<AlbumDto>> getAlbumsList() {
        return ResponseEntity.ok(albumService.getList());
    }

    @GetMapping("{username}")
    public ResponseEntity<List<AlbumDto>> getSignerAlbumsList(@PathVariable @Size(min = 1, max = 50) String username) {
        return ResponseEntity.ok(albumService.getSignerAlbums(username));
    }

    @PostMapping()
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody @Validated Album album) {
        try {
            return ResponseEntity.ok(albumService.create(album));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{albumName}")
    public ResponseEntity<Void> deleteAlbum(@RequestParam("singerName") @Size(min = 1, max = 50) String singerName,
                                            @PathVariable @Size(min = 1, max = 50) String albumName) {
        try {
            albumService.delete(albumName, singerName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
