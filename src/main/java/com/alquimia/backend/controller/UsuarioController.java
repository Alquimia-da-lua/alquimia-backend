package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.AtualizarUsuarioRequestDTO;
import com.alquimia.backend.dto.request.EnderecoRequestDTO;
import com.alquimia.backend.dto.request.LoginRequestDTO;
import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.ClienteResponseDTO;
import com.alquimia.backend.dto.response.LoginResponseDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.exception.CepNaoEncontradoException;
import com.alquimia.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/buscar/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable("cdUsuario") Integer cdUsuario){
        var usuario = usuarioService.buscarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    @PutMapping("/atualizar/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable("cdUsuario") Integer cdUsuario,
                                                               @RequestBody @Valid AtualizarUsuarioRequestDTO usuarioDto) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(usuarioDto, cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @PutMapping("/deletar/{cdUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("cdUsuario") Integer cdUsuario){
        usuarioService.deletarUsuario(cdUsuario);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/listar/ativos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuariosAtivos(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuariosAtivos());
    }

    @PostMapping("/{cdUsuario}/endereco")
    public ResponseEntity<ClienteResponseDTO> cadastrarEndereco(@PathVariable("cdUsuario") Integer cdUsuario,
                                                                @RequestBody @Valid EnderecoRequestDTO enderecoDto) throws CepNaoEncontradoException {

        ClienteResponseDTO response = usuarioService.cadastrarEndereco(cdUsuario, enderecoDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
