package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

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

        }
        var usuario = new Usuario();

        usuario.setNmUsuario(requestDTO.nmUsuario());
        usuario.setEmailUsuario(requestDTO.emailUsuario());
        usuario.setSenhaUsuario(requestDTO.senhaUsuario());
        usuario.setRoleUsuario(requestDTO.roleUsuario());
        usuario.setNuTelefone(requestDTO.nuTelefone());
        usuario.setAtivo(true);
        usuario.setNuCpf(requestDTO.nuCpf());

        var usuarioSalvo =  usuarioRepository.save(usuario);

        return responseDto(usuarioSalvo);
    }

    public UsuarioResponseDTO buscarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return responseDto(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<UsuarioResponseDTO> usuarios = usuarioRepository.findAll()
                .stream()
                .map(this::responseDto)
                .toList();

        return usuarios;
    }

    public void deletarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        usuarioRepository.deleteById(cdUsuario);
    }

    public List<UsuarioResponseDTO> listarUsuariosAtivos() {
        List<UsuarioResponseDTO> usuariosAtivos = usuarioRepository.findAllByIsAtivo(true)
                .stream()
                .map(this::responseDto)
                .toList();

        return usuariosAtivos;
    }
}