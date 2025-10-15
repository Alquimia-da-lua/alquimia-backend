package com.alquimia.backend.service;

import com.alquimia.backend.model.Cliente;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Usuario cadastrarCliente(Cliente cliente) {
        cliente.setAtivo(true);

        return clienteRepository.save(cliente);
    }
}
