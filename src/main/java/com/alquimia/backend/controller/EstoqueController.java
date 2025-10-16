package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.EstoqueRequestDTO;
import com.alquimia.backend.dto.response.EstoqueResponseDTO;
import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.service.EstoqueService;
import com.alquimia.backend.service.ItemEstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    EstoqueService estoqueService;

    @Autowired
    ItemEstoqueService itemEstoqueService;

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> criarEstoque(@RequestBody @Valid EstoqueRequestDTO dto){
        var resp = estoqueService.criarEstoque(dto.isAtivo());

        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponseDTO>> listarEstoquesAtivos (){
        var listaEstoques = estoqueService.listarEstoquesAtivos();

        return ResponseEntity.ok(listaEstoques);
    }

    //ativo ou nao
    @GetMapping("/{cdEstoque}")
    public ResponseEntity<EstoqueResponseDTO> buscarEstoquePorId(@PathVariable Integer cdEstoque){
        var resp = estoqueService.buscarEstoquePorId(cdEstoque);

        return ResponseEntity.ok(resp);
    }

    @GetMapping({"/ativo/{cdEstoque}"})
    public ResponseEntity<EstoqueResponseDTO> buscarEstoqueAtivoPorId(@PathVariable Integer cdEstoque){
        var resp = estoqueService.buscarEstoqueAtivoPorId(cdEstoque);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{cdEstoque}/itens")
    public ResponseEntity<List<ItemEstoqueResponseDTO>> listarItens (@PathVariable Integer cdEstoque){
        var resp = itemEstoqueService.listarItensDoEstoque(cdEstoque);

        return ResponseEntity.ok(resp);
    }

    @PatchMapping("/{cdEstoque}/desativar")
    public void desativar (@PathVariable Integer cdEstoque){
        estoqueService.desativarEstoque(cdEstoque);
    }

    @PatchMapping(("/{cdEstoque}/reativar"))
    public void reativar (@PathVariable Integer cdEstoque){
        estoqueService.reativarEstoque(cdEstoque);
    }

}
