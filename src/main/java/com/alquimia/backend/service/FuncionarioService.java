package com.alquimia.backend.service;

import com.alquimia.backend.model.Funcionario;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Usuario cadastrarFuncionario(Funcionario funcionario){
        funcionario.setAtivo(true);

        return funcionarioRepository.save(funcionario);
    }
}
