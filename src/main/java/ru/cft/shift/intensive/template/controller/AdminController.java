package ru.cft.shift.intensive.template.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.entity.Singer;
import ru.cft.shift.intensive.template.service.SingerService;
import ru.cft.shift.intensive.template.service.UsersService;

import java.util.List;

@RestController
@Validated
@RequestMapping("admin")
@Tag(name = "api.admin.tag.name", description = "api.admin.tag.description")
public class AdminController {
    private final UsersService usersService;
    private final SingerService singerService;

    @Autowired
    public AdminController(UsersService usersService, SingerService singerService) {
        this.usersService = usersService;
        this.singerService = singerService;
    }

    @Operation(summary = "api.admin.users.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.admin.users.api-responses.200.description"),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("users")
    public ResponseEntity<List<UsernameDto>> getUsersList() {
        return ResponseEntity.ok(usersService.list());
    }

    @Operation(summary = "api.admin.delete.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.admin.delete.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.admin.delete.api-responses.404.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @DeleteMapping("deleteUser/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Size(min = 3, max = 50) String username) {
        usersService.delete(username);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "api.admin.singers.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.admin.singers.api-responses.200.description"),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("singers")
    public ResponseEntity<List<SingerDto>> getSignersList() {
        return ResponseEntity.ok(singerService.getList());
    }

    @Operation(summary = "api.admin.singer.create.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.admin.singer.create.api-responses.200.description"),
            @ApiResponse(responseCode = "409", description = "api.admin.singer.create.api-responses.409.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @PostMapping("singer")
    public ResponseEntity<SingerDto> createSigner(@RequestBody @Valid Singer singer) {
        return ResponseEntity.ok(singerService.create(singer));
    }

    @Operation(summary = "api.admin.singer.delete.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.admin.singer.delete.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.admin.singer.delete.api-responses.404.description",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @DeleteMapping("deleteSinger/{singerName}")
    public ResponseEntity<Void> deleteSigner(@PathVariable @Size(min = 3, max = 50) String singerName) {
        singerService.delete(singerName);
        return ResponseEntity.ok().build();
    }
}
