package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO responseDto(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getEmailUsuario(),
                usuario.getRoleUsuario(),
                usuario.getNuTelefone(),
                usuario.isAtivo(),
                usuario.getNuCpf()
        );
        return dto;
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO requestDTO){
        if(usuarioRepository.findByEmailUsuario(requestDTO.emailUsuario()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email já cadastrado"
            );
        }
        var usuario = new Usuario();
        BeanUtils.copyProperties(requestDTO, usuario);
        usuario.setAtivo(true);

        var usuarioSalvo =  usuarioRepository.save(usuario);

        return responseDto(usuarioSalvo);
    }

    public UsuarioResponseDTO buscarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );

        return responseDto(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::responseDto)
                .toList();
    }

    public void deletarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );

        usuario.setAtivo(false);
    }

    public List<UsuarioResponseDTO> listarUsuariosAtivos() {
        return usuarioRepository.findAllByIsAtivo(true)
                .stream()
                .map(this::responseDto)
                .toList();
    }
}