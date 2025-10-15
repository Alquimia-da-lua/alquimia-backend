package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDto){
        UsuarioResponseDTO novoUsuario = usuarioService.cadastrarUsuario(usuarioDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoUsuario);
    }

    @GetMapping("/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable("cdUsuario") int cdUsuario){
        var usuario = usuarioService.buscarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuario(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.listarUsuarios());
    }

    @DeleteMapping("/{cdUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("cdUsuario") int cdUsuario){
        usuarioService.deletarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
