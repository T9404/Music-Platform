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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.template.dto.SingerDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.entity.Users;
import ru.cft.shift.intensive.template.service.SingerService;
import ru.cft.shift.intensive.template.service.UsersService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("user")
@Tag(name = "api.user.tag.name", description = "api.user.tag.description")
public class UserController {
    private final UsersService usersService;
    private final SingerService singerService;

    @Autowired
    public UserController(UsersService usersService, SingerService singerService) {
        this.usersService = usersService;
        this.singerService = singerService;
    }

    @Operation(summary = "api.user.create.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.user.create.api-responses.200.description"),
            @ApiResponse(responseCode = "409", description = "api.user.create.api-responses.409.description",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @PostMapping()
    public ResponseEntity<UsernameDto> create(@RequestBody @Valid Users user) {
        usersService.create(user);
        return ResponseEntity.ok(new UsernameDto(user.getUsername()));
    }

    @Operation(summary = "api.user.get.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.get.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.user.get.api-responses.404.description",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("{username}")
    public ResponseEntity<UsernameDto> getUser(@PathVariable @Size(min = 3, max = 50) String username) {
        return ResponseEntity.ok(new UsernameDto(usersService.findByUsername(username).username()));
    }

    @Operation(summary = "api.signer.get.operation.summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "api.get.api-responses.200.description"),
            @ApiResponse(responseCode = "404", description = "api.signer.get.api-responses.404.description",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "api.server.error",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorControllerAdvice.ErrorResponse.class))})
    })
    @GetMapping("signer/{signerName}")
    public ResponseEntity<SingerDto> getSigner(@PathVariable @Size(min = 3, max = 50) String signerName) {
        return ResponseEntity.ok(singerService.findBySignerName(signerName));
    }
}
