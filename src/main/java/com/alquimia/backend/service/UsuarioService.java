package com.alquimia.backend.service;

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

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO requestDTO){
        if(usuarioRepository.findByEmailUsuario(requestDTO.emailUsuario()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email já cadastrado"
            );
        }
        if(usuarioRepository.findByNuCpf(requestDTO.nuCpf()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "CPF já cadastrado"
            );
        }

        Usuario usuario = new Usuario();

        // criação de usuário dependendo da role, talvez retirar a funcao de cadastrar funcionario por aqui no futuro
        if (requestDTO.roleUsuario() == RoleUsuario.CLIENTE) {
            Cliente cliente = new Cliente();
            BeanUtils.copyProperties(requestDTO, cliente);

            usuario = clienteService.cadastrarCliente(cliente);

        } else if (requestDTO.roleUsuario() == RoleUsuario.FUNCIONARIO) {
            Funcionario funcionario = new Funcionario();
            BeanUtils.copyProperties(requestDTO, funcionario);

            usuario = funcionarioService.cadastrarFuncionario(funcionario);
        }
        return responseDto(usuario);
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

    public UsuarioResponseDTO atualizarUsuario(Integer cdUsuario, UsuarioRequestDTO requestDTO){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );

        BeanUtils.copyProperties(requestDTO, usuario, "cdUsuario", "isAtivo", "roleUsuario");

        return responseDto(usuarioRepository.save(usuario));
    }

    public void deletarUsuario(Integer cdUsuario){
        var usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );

        usuario.setAtivo(false);
    }

    public List<UsuarioResponseDTO> listarUsuariosAtivos() {
        return usuarioRepository.findAllByIsAtivoTrue()
                .stream()
                .map(this::responseDto)
                .toList();
    }
}