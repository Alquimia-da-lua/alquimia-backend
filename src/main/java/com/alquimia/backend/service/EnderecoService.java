package com.alquimia.backend.service;

import com.alquimia.backend.client.ViaCepClient;
import com.alquimia.backend.dto.request.EnderecoRequestDTO;
import com.alquimia.backend.dto.response.EnderecoResponseDTO;
import com.alquimia.backend.dto.response.ViaCepDTO;
import com.alquimia.backend.exception.CepNaoEncontradoException;
import com.alquimia.backend.exception.EnderecoNaoEncontradoException;
import com.alquimia.backend.model.Endereco;
import com.alquimia.backend.repository.ClienteRepository;
import com.alquimia.backend.repository.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ViaCepClient viaCepClient;

    public EnderecoService(EnderecoRepository enderecoRepository, ViaCepClient viaCepClient) {
        this.enderecoRepository = enderecoRepository;
        this.viaCepClient = viaCepClient;
    }

    @Transactional
    public EnderecoResponseDTO cadastrarEndereco(EnderecoRequestDTO enderecoDTO)
            throws CepNaoEncontradoException {

        ViaCepDTO endereco = this.viaCepClient.buscarCep(enderecoDTO.nuCep()).getBody();

        if (endereco == null || endereco.cep() == null) {
            throw new CepNaoEncontradoException(enderecoDTO.nuCep());
        }

        var novo = new Endereco();
        novo.setNuCep(endereco.cep().replace("-", ""));
        novo.setDsBairro(endereco.bairro());
        novo.setDsLogradouro(endereco.logradouro());
        novo.setDsLocalidade(endereco.localidade());
        novo.setNmEstado(endereco.uf());

        novo.setDsComplemento(enderecoDTO.dsComplemento());

        Endereco enderecoSalvo = enderecoRepository.save(novo);
        return new EnderecoResponseDTO(enderecoSalvo);
    }

    @Transactional
    public EnderecoResponseDTO buscarEndereco(Integer cdEndereco){
        return this.enderecoRepository.findByCdEndereco(cdEndereco)
                .map(EnderecoResponseDTO::new)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(cdEndereco));
    }


    @Transactional
    public EnderecoResponseDTO alterarEndereco(Integer cdEndereco, EnderecoRequestDTO enderecoDTO)
            throws CepNaoEncontradoException {

        Endereco enderecoParaAtualizar = this.enderecoRepository.findByCdEndereco(cdEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(cdEndereco));
        
        if (!Objects.equals(enderecoDTO.nuCep(), enderecoParaAtualizar.getNuCep())) {
            ViaCepDTO endereco = this.viaCepClient.buscarCep(enderecoDTO.nuCep()).getBody();

            if (endereco == null || endereco.cep() == null) {
                throw new CepNaoEncontradoException(enderecoDTO.nuCep());
            }

            enderecoParaAtualizar.setNuCep(endereco.cep().replace("-", ""));
            enderecoParaAtualizar.setDsBairro(endereco.bairro());
            enderecoParaAtualizar.setDsLogradouro(endereco.logradouro());
            enderecoParaAtualizar.setDsLocalidade(endereco.localidade());
            enderecoParaAtualizar.setNmEstado(endereco.uf());
        }

        BeanUtils.copyProperties(enderecoDTO, enderecoParaAtualizar);

        Endereco enderecoSalvo = enderecoRepository.save(enderecoParaAtualizar);
        return new EnderecoResponseDTO(enderecoSalvo);
    }

    @Transactional
    public void deletarEndereco(Integer cdEndereco){
        Endereco endereco = enderecoRepository.findByCdEndereco(cdEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(cdEndereco));
        enderecoRepository.delete(endereco);
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarEnderecoCliente(Integer cdCliente) {
        Endereco endereco = enderecoRepository.findByClienteId(cdCliente)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado para o cliente com ID: " + cdCliente));

        return new EnderecoResponseDTO(endereco);
    }



}
