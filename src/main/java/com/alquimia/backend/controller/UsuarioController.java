package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.AtualizarUsuarioRequestDTO;
import com.alquimia.backend.dto.request.LoginRequestDTO;
import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.LoginResponseDTO;
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

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDto){
        UsuarioResponseDTO novoUsuario = usuarioService.cadastrarUsuario(usuarioDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/buscar/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable("cdUsuario") int cdUsuario){
        var usuario = usuarioService.buscarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    // funcao incompleta, esperar pelo jwt
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginDto){
        LoginResponseDTO login = usuarioService.login(loginDto);

        return ResponseEntity.status(HttpStatus.OK).body(login);
    }

    @PutMapping("/atualizar/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable("cdUsuario") int cdUsuario,
                                                               @RequestBody @Valid AtualizarUsuarioRequestDTO usuarioDto) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(usuarioDto, cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @PutMapping("/deletar/{cdUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("cdUsuario") int cdUsuario){
        usuarioService.deletarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/listar/ativos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosAtivos(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuariosAtivos());
    }
}
