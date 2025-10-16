package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.ProdutoRequestDTO;
import com.alquimia.backend.dto.response.ProdutoResponseDTO;
import com.alquimia.backend.model.Produto;
import com.alquimia.backend.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrarProduto(@RequestBody @Valid
                                                                   ProdutoRequestDTO produto){
       ProdutoResponseDTO response = this.produtoService.cadastrarProduto(produto);
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos(Produto produto){
        var lista = produtoService.listarProdutos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/buscar/{cdProduto}")
    public ResponseEntity<ProdutoResponseDTO> buscarProduto(@PathVariable("cdProduto") Integer cdProduto){
        ProdutoResponseDTO response = produtoService.buscarProduto(cdProduto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/listar/ativos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutosAtivos(Produto produto){
        var lista = produtoService.listarProdutosAtivos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/alterar/{cdProduto}")
    public ResponseEntity<ProdutoResponseDTO> alterarProduto(@PathVariable("cdProduto") Integer cdProduto,
                                                             @RequestBody @Valid ProdutoRequestDTO produtoDTO){
        var response = produtoService.alterarProduto(cdProduto, produtoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{cdProduto}")
    public ResponseEntity<ProdutoResponseDTO> deletarProduto(@PathVariable("cdProduto") Integer cdProduto){
        this.produtoService.deletarProduto(cdProduto);
        return ResponseEntity.noContent().build();
    }





}
