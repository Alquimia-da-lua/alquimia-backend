package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.model.Usuario;
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

    @GetMapping("/{cdUsuario")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable("cdUsuario") int cdUsuario){
        var usuario = usuarioService.buscarUsuario(cdUsuario);

        if(usuario != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuario);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuario(){
        var usuarios = usuarioService.listarUsuarios();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarios);
    }

    @DeleteMapping("/{cdUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("cdUsuario") int cdUsuario){
        var usuario = usuarioService.deletarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
