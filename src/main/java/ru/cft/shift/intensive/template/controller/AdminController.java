package ru.cft.shift.intensive.template.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.template.dto.SignerDto;
import ru.cft.shift.intensive.template.dto.UsernameDto;
import ru.cft.shift.intensive.template.entity.Signer;
import ru.cft.shift.intensive.template.service.SignerService;
import ru.cft.shift.intensive.template.service.UsersService;

import java.util.List;

@RestController
@Validated
@RequestMapping("admin")
public class AdminController {
    private final UsersService usersService;
    private final SignerService signerService;

    @Autowired
    public AdminController(UsersService usersService, SignerService signerService) {
        this.usersService = usersService;
        this.signerService = signerService;
    }

    @GetMapping("users")
    public ResponseEntity<List<UsernameDto>> getUsersList() {
        return ResponseEntity.ok(usersService.list());
    }

    @DeleteMapping("deleteUser/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Size(min = 3, max = 50) String username) {
        usersService.delete(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("signers")
    public ResponseEntity<List<SignerDto>> getSignersList() {
        return ResponseEntity.ok(signerService.getList());
    }

    @PostMapping("signer")
    public ResponseEntity<SignerDto> createSigner(@RequestBody @Valid Signer signer) {
        return ResponseEntity.ok(signerService.create(signer));
    }

    @DeleteMapping("deleteSigner/{signerName}")
    public ResponseEntity<Void> deleteSigner(@PathVariable @Size(min = 3, max = 50) String signerName) {
        signerService.delete(signerName);
        return ResponseEntity.ok().build();
    }
}
