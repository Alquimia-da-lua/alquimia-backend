package com.alquimia.backend.client;

import com.alquimia.backend.dto.response.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep-client", url = "viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    ResponseEntity<ViaCepDTO> buscarCep(@PathVariable("cep") String cep);
}
