package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.LoginRequestDTO;
import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.LoginResponseDTO;
import com.alquimia.backend.dto.response.RefreshTokenResponseDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.service.AuthenticationService;
import com.alquimia.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginDto) {
        LoginResponseDTO response = authenticationService.login(loginDto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDto){
        UsuarioResponseDTO novoUsuario = usuarioService.cadastrarUsuario(usuarioDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        RefreshTokenResponseDTO response = authenticationService.refreshToken(refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
