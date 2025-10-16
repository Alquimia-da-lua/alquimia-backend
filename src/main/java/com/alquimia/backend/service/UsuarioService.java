package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.AtualizarUsuarioRequestDTO;
import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.enums.RoleUsuario;
import com.alquimia.backend.model.Cliente;
import com.alquimia.backend.model.Funcionario;
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
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    public UsuarioService(UsuarioRepository usuarioRepository, ClienteService clienteService, FuncionarioService funcionarioService) {
        this.clienteService = clienteService;
        this.usuarioRepository = usuarioRepository;
        this.funcionarioService = funcionarioService;
    }

    public UsuarioResponseDTO responseDto(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getEmailUsuario(),
                usuario.getRoleUsuario(),
                usuario.getNuTelefone(),
                usuario.isAtivo(),
                usuario.getNuCpf()
        );
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO requestDto){
        if(usuarioRepository.findByEmailUsuario(requestDto.emailUsuario()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        if(usuarioRepository.findByNuCpf(requestDto.nuCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }

        Usuario usuario;

        // criação de usuário dependendo da role, talvez retirar a funcao de cadastrar funcionario por aqui no futuro
        if (requestDto.roleUsuario() == RoleUsuario.CLIENTE) {
            Cliente cliente = new Cliente();
            BeanUtils.copyProperties(requestDto, cliente);

            usuario = clienteService.cadastrarCliente(cliente);

        } else if (requestDto.roleUsuario() == RoleUsuario.FUNCIONARIO) {
            Funcionario funcionario = new Funcionario();
            BeanUtils.copyProperties(requestDto, funcionario);

            usuario = funcionarioService.cadastrarFuncionario(funcionario);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role de usuário inválida");
        }
        return responseDto(usuario);
    }

    public UsuarioResponseDTO buscarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return responseDto(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::responseDto)
                .toList();
    }

    public UsuarioResponseDTO atualizarUsuario(AtualizarUsuarioRequestDTO requestDto, Integer cdUsuario){
        Usuario usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if(usuarioRepository.findByEmailUsuario(requestDto.emailUsuario()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");

        } else if (requestDto.emailUsuario() != null && !requestDto.emailUsuario().isBlank()
                // checando se o email ja nao é do próprio usuário
                && !requestDto.emailUsuario().equals(usuario.getEmailUsuario())) {
            usuario.setEmailUsuario(requestDto.emailUsuario());
        }

        if(usuarioRepository.findByNuCpf(requestDto.nuCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");

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
        return responseDto(usuarioRepository.save(usuario));
    }

    public void deletarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuariosAtivos() {
        return usuarioRepository.findAllByIsAtivoTrue()
                .stream()
                .map(this::responseDto)
                .toList();
    }
}