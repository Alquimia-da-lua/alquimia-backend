package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.AtualizarUsuarioRequestDTO;
import com.alquimia.backend.dto.request.LoginRequestDTO;
import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.LoginResponseDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.enums.RoleUsuario;
import com.alquimia.backend.exception.DadoDuplicadoException;
import com.alquimia.backend.exception.UsuarioNaoEncontradoException;
import com.alquimia.backend.model.Cliente;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    public UsuarioService(UsuarioRepository usuarioRepository, ClienteService clienteService, FuncionarioService funcionarioService) {
        this.clienteService = clienteService;
        this.usuarioRepository = usuarioRepository;
        this.funcionarioService = funcionarioService;
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO requestDto){
        if(usuarioRepository.findByEmailUsuario(requestDto.emailUsuario()).isPresent()) {
            throw new DadoDuplicadoException("Email já cadastrado");
        }

        if(usuarioRepository.findByNuCpf(requestDto.nuCpf()).isPresent()) {
            throw new DadoDuplicadoException("CPF já cadastrado");
        }

        Usuario usuario;

        // criação de usuário (somente cliente)
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(requestDto, cliente);
        cliente.setRoleUsuario(RoleUsuario.CLIENTE);
        usuario = clienteService.cadastrarCliente(cliente);

        return new UsuarioResponseDTO(usuario);
    }

    public UsuarioResponseDTO buscarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        return new UsuarioResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<UsuarioResponseDTO> usuarios = new ArrayList<>();

        List <Usuario> modelUsuario = usuarioRepository.findAll();
        for (Usuario usuario : modelUsuario) {
            usuarios.add(new UsuarioResponseDTO(usuario));
        }

        return usuarios;
    }

    // funcao incompleta, esperar pelo jwt
    public LoginResponseDTO login(LoginRequestDTO loginDto){
        var usuario = usuarioRepository.findByEmailUsuario(loginDto.emailUsuario())
                .orElseThrow(UsuarioNaoEncontradoException::new);

        return new LoginResponseDTO("");
    }

    public UsuarioResponseDTO atualizarUsuario(AtualizarUsuarioRequestDTO requestDto, Integer cdUsuario){
        Usuario usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        if(usuarioRepository.findByEmailUsuario(requestDto.emailUsuario()).isPresent()) {
            throw new DadoDuplicadoException("Email já cadastrado");

        } else if (requestDto.emailUsuario() != null && !requestDto.emailUsuario().isBlank()
                // checando se o email ja nao é do próprio usuário
                && !requestDto.emailUsuario().equals(usuario.getEmailUsuario())) {
            usuario.setEmailUsuario(requestDto.emailUsuario());
        }

        if(usuarioRepository.findByNuCpf(requestDto.nuCpf()).isPresent()) {
            throw new DadoDuplicadoException("CPF já cadastrado");

        } else if (requestDto.nuCpf() != null && !requestDto.nuCpf().isBlank()
                && !requestDto.nuCpf().equals(usuario.getNuCpf())) {
            usuario.setNuCpf(requestDto.nuCpf());
        }

        if (requestDto.nmUsuario() != null && !requestDto.nmUsuario().isBlank()) {
            usuario.setNmUsuario(requestDto.nmUsuario());
        }

        if (requestDto.senhaUsuario() != null && !requestDto.senhaUsuario().isBlank()) {
            usuario.setSenhaUsuario(requestDto.senhaUsuario());
        }

        if (requestDto.nuTelefone() != null && !requestDto.nuTelefone().isBlank()) {
            usuario.setNuTelefone(requestDto.nuTelefone());
        }
        return new UsuarioResponseDTO(usuarioRepository.save(usuario));
    }

    public void deletarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuariosAtivos() {
        List<UsuarioResponseDTO> usuarios = new ArrayList<>();

        List <Usuario> modelUsuario = usuarioRepository.findAllByIsAtivoTrue();
        for (Usuario usuario : modelUsuario) {
            usuarios.add(new UsuarioResponseDTO(usuario));
        }
        return usuarios;
    }
}