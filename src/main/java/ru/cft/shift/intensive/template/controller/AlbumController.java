package ru.cft.shift.intensive.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "api.album.tag.name", description = "api.album.tag.description")
public class AlbumController {
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(summary = "api.albums.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.albums.api-responses.200.description"),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping()
    public ResponseEntity<List<AlbumDto>> getAlbumsList() {
        return ResponseEntity.ok(albumService.getList());
    }

    @Operation(summary = "api.albums.signer.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.albums.signer.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.albums.signer.api-responses.404.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("getByUsername/{username}")
    public ResponseEntity<List<AlbumDto>> getSignerAlbumsList(@PathVariable @Size(min = 1, max = 50) String username) {
        return ResponseEntity.ok(albumService.getSignerAlbums(username));
    }

    @Operation(summary = "api.album.post.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.album.post.api-responses.200.description"),
            @ApiResponse(responseCode = "409", description = "api.album.post.api-responses.409.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @PostMapping()
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody @Validated Album album) {
        return ResponseEntity.ok(albumService.create(album));
    }

    @Operation(summary = "api.album.delete.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.album.delete.api-responses.200.description"),
            @ApiResponse(responseCode = "409", description = "api.album.delete.api-responses.409.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @DeleteMapping("{albumName}")
    public ResponseEntity<Void> deleteAlbum(@RequestParam("singerName") @Size(min = 1, max = 50) String singerName,
                                            @PathVariable @Size(min = 1, max = 50) String albumName) {
        albumService.deleteAlbum(albumName, singerName);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "api.album.song.post.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.album.song.post.api-responses.200.description"),
            @ApiResponse(responseCode = "409", description = "api.album.song.post.api-responses.409.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @PostMapping("addSong/{albumName}")
    public ResponseEntity<AlbumDto> addSong(@PathVariable @Size(min = 1, max = 50) String albumName,
                                            @RequestBody @Validated Song song) {
        return ResponseEntity.ok(albumService.addSong(albumName, song));
    }

    @Operation(summary = "api.album.song.delete.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.album.song.delete.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.album.song.delete.api-responses.404.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @DeleteMapping("deleteSong/{albumName}/{songName}")
    public ResponseEntity<Void> deleteSong(@RequestParam("ownerName") @Size(min = 1, max = 50) String ownerName,
                                            @PathVariable @Size(min = 1, max = 50) String albumName,
                                            @PathVariable @Size(min = 1, max = 50) String songName) {
        albumService.deleteSong(albumName, ownerName, songName);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "api.album.get.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.album.get.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.album.api-responses.404.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("getByGenre/{genre}")
    public ResponseEntity<List<AlbumDto>> getAlbumsByGenre(@PathVariable @Size(min = 1, max = 50) String genre) {
        return ResponseEntity.ok(albumService.getAlbumsByGenre(genre));
    }
}
