package com.alquimia.backend.controller;

import com.alquimia.backend.client.ViaCepClient;
import com.alquimia.backend.dto.request.EnderecoRequestDTO;
import com.alquimia.backend.dto.response.EnderecoResponseDTO;
import com.alquimia.backend.dto.response.ViaCepDTO;
import com.alquimia.backend.exception.CepNaoEncontradoException;
import com.alquimia.backend.repository.EnderecoRepository;
import com.alquimia.backend.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/endereco")
public class EnderecoController {

    private final ViaCepClient viaCepClient;
    private final EnderecoService enderecoService;

    public EnderecoController(ViaCepClient viaCepClient, EnderecoRepository enderecoRepository, EnderecoService enderecoService) {
        this.viaCepClient = viaCepClient;
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepDTO> buscarPorCep(@PathVariable("cep") String cep) {
        return this.viaCepClient.buscarCep(cep);
    }

    @DeleteMapping("/{cdEndereco}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable("cdEndereco") Integer cdEndereco) {
        enderecoService.deletarEndereco(cdEndereco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> cadastrarEndereco(@RequestBody@Valid EnderecoRequestDTO endereco) throws CepNaoEncontradoException {
        EnderecoResponseDTO response = this.enderecoService.cadastrarEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/{cdEndereco}")
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(@PathVariable("cdEndereco") Integer cdEndereco) {
        EnderecoResponseDTO response = this.enderecoService.buscarEndereco(cdEndereco);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/alterar/{cdEndereco}")
    public ResponseEntity<EnderecoResponseDTO> alterarEndereco(@PathVariable("cdEndereco") Integer cdEndereco,
                                                               @RequestBody@Valid EnderecoRequestDTO enderecoDTO) throws CepNaoEncontradoException {
        var response = enderecoService.alterarEndereco(cdEndereco, enderecoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/cliente/{cdCliente}")
    public ResponseEntity<EnderecoResponseDTO> buscaEnderecoCliente(@PathVariable Integer cdCliente) {
        EnderecoResponseDTO endereco = enderecoService.buscarEnderecoCliente(cdCliente);
        return ResponseEntity.ok(endereco);
    }

}
