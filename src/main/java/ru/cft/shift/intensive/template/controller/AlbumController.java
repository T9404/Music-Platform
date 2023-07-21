package ru.cft.shift.intensive.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.template.dto.AlbumDto;
import ru.cft.shift.intensive.template.entity.Album;
import ru.cft.shift.intensive.template.entity.Song;
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

    @GetMapping("getByUsername/{username}")
    public ResponseEntity<List<AlbumDto>> getSignerAlbumsList(@PathVariable @Size(min = 1, max = 50) String username) {
        return ResponseEntity.ok(albumService.getSignerAlbums(username));
    }

    @PostMapping()
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody @Validated Album album) {
        return ResponseEntity.ok(albumService.create(album));
    }

    @DeleteMapping("{albumName}")
    public ResponseEntity<Void> deleteAlbum(@RequestParam("singerName") @Size(min = 1, max = 50) String singerName,
                                            @PathVariable @Size(min = 1, max = 50) String albumName) {
        albumService.deleteAlbum(albumName, singerName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("addSong/{albumName}")
    public ResponseEntity<AlbumDto> addSong(@PathVariable @Size(min = 1, max = 50) String albumName,
                                            @RequestBody @Validated Song song) {
        return ResponseEntity.ok(albumService.addSong(albumName, song));
    }

    @DeleteMapping("deleteSong/{albumName}/{songName}")
    public ResponseEntity<Void> deleteSong(@RequestParam("ownerName") @Size(min = 1, max = 50) String ownerName,
                                            @PathVariable @Size(min = 1, max = 50) String albumName,
                                            @PathVariable @Size(min = 1, max = 50) String songName) {
        albumService.deleteSong(albumName, ownerName, songName);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get a list of albums by genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of albums by genre"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("getByGenre/{genre}")
    public ResponseEntity<List<AlbumDto>> getAlbumsByGenre(@PathVariable @Size(min = 1, max = 50) String genre) {
        return ResponseEntity.ok(albumService.getAlbumsByGenre(genre));
    }
}
