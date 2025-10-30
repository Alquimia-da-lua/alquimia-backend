package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.EnderecoRequestDTO;
import com.alquimia.backend.dto.response.ClienteResponseDTO;
import com.alquimia.backend.dto.response.EnderecoResponseDTO;
import com.alquimia.backend.exception.CepNaoEncontradoException;
import com.alquimia.backend.exception.UsuarioNaoEncontradoException;
import com.alquimia.backend.model.Cliente;
import com.alquimia.backend.model.Endereco;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.ClienteRepository;
import com.alquimia.backend.repository.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
    }

    public Usuario cadastrarCliente(Cliente cliente) {
        cliente.setAtivo(true);

        return clienteRepository.save(cliente);
    }

    public ClienteResponseDTO cadastrarEndereco(Integer cdCliente, EnderecoRequestDTO enderecoDto) throws CepNaoEncontradoException {
        Cliente cliente = clienteRepository.findById(cdCliente)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        EnderecoResponseDTO enderecoResponse = enderecoService.cadastrarEndereco(enderecoDto);

        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoResponse, endereco);

        cliente.setCdEndereco(endereco);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return new ClienteResponseDTO(clienteSalvo);
    }
}
