package com.alquimia.backend.dto.response;

import com.alquimia.backend.model.Endereco;

public record EnderecoResponseDTO(
        Integer cdEndereco,
        String nuCep,
        String dsLogradouro,
        String dsComplemento,
        String dsBairro,
        String dsLocalidade,
        String nmEstado) {

    public EnderecoResponseDTO(Endereco endereco) {
        this(
                endereco.getCdEndereco(),
                endereco.getNuCep(),
                endereco.getDsLogradouro(),
                endereco.getDsComplemento(),
                endereco.getDsBairro(),
                endereco.getDsLocalidade(),
                endereco.getNmEstado()
        );
    }
}
