package ru.cft.shift.intensive.template.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.service.UsersService;

import java.util.List;

@RestController
@Validated
@RequestMapping("admin")
public class AdminController {
    private final UsersService usersService;

    @Autowired
    public AdminController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("users")
    public ResponseEntity<List<UsernameDto>> getList() {
        return ResponseEntity.ok(usersService.list());
    }

    /*@PostMapping("users")
    public ResponseEntity<UsernameDto> create(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(usersService.create(userDto));
    }*/

    @DeleteMapping("delete/{username}")
    public ResponseEntity<Void> delete(@PathVariable @Size(min = 3, max = 50) String username) {
        usersService.delete(username);
        return ResponseEntity.ok().build();
    }
}

// сделать здесь добавление signer, вместо user
