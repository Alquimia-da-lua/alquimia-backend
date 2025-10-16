package com.alquimia.backend.service;

import com.alquimia.backend.model.Funcionario;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Usuario cadastrarFuncionario(Funcionario funcionario){
        funcionario.setAtivo(true);

        // funcao para gerar o numero de matricula aleatorio
        Random random = new Random();
        int nuMatricula;

        // repete caso gere um numero de matricula que ja exista
        do {
            nuMatricula = random.nextInt(100000, 999999);
        } while (funcionarioRepository.findByNuMatricula(nuMatricula).isPresent());

        funcionario.setNuMatricula(nuMatricula);

        return funcionarioRepository.save(funcionario);
    }
}
