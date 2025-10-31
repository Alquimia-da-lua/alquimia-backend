package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.AtualizarUsuarioRequestDTO;
import com.alquimia.backend.dto.request.EnderecoRequestDTO;
import com.alquimia.backend.dto.request.UsuarioRequestDTO;
import com.alquimia.backend.dto.response.ClienteResponseDTO;
import com.alquimia.backend.dto.response.EnderecoResponseDTO;
import com.alquimia.backend.dto.response.UsuarioResponseDTO;
import com.alquimia.backend.enums.RoleUsuario;
import com.alquimia.backend.exception.CepNaoEncontradoException;
import com.alquimia.backend.exception.DadoDuplicadoException;
import com.alquimia.backend.exception.UsuarioNaoEncontradoException;
import com.alquimia.backend.model.Cliente;
import com.alquimia.backend.model.Endereco;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.ClienteRepository;
import com.alquimia.backend.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, EnderecoService enderecoService,
                          ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.enderecoService = enderecoService;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
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

        cliente.setSenhaUsuario(passwordEncoder.encode(requestDto.senhaUsuario()));
        cliente.setRoleUsuario(RoleUsuario.CLIENTE);
        cliente.setAtivo(true);
        usuario = clienteRepository.save(cliente);

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
            usuario.setSenhaUsuario(passwordEncoder.encode(requestDto.senhaUsuario()));
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

    public ClienteResponseDTO cadastrarEndereco(Integer cdUsuario, EnderecoRequestDTO enderecoDto) throws CepNaoEncontradoException {
        Usuario usuario = usuarioRepository.findByCdUsuario(cdUsuario)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        if (!usuario.getRoleUsuario().equals(RoleUsuario.CLIENTE)) {
            throw new IllegalArgumentException("Usuário não é um cliente");
        }

        EnderecoResponseDTO enderecoResponse = enderecoService.cadastrarEndereco(enderecoDto);

        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoResponse, endereco);

        Cliente cliente = (Cliente) usuario;
        cliente.setCdEndereco(endereco);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return new ClienteResponseDTO(clienteSalvo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));
    }
}