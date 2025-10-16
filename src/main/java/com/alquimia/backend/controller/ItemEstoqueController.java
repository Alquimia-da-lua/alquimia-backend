package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.ItemEstoqueRequestDTO;
import com.alquimia.backend.dto.response.ItemEstoqueResponseDTO;
import com.alquimia.backend.model.ItemEstoque;
import com.alquimia.backend.service.EstoqueService;
import com.alquimia.backend.service.ItemEstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itemestoque")
public class ItemEstoqueController {
    @Autowired
    ItemEstoqueService itemEstoqueService;

    @Autowired
    EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<ItemEstoqueResponseDTO> criarOuAtualizar(@RequestBody @Valid ItemEstoqueRequestDTO dto){
        var resp = itemEstoqueService.criarOuAtualizarItem(dto);

        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping("/{cdItemEstoque}")
    public ResponseEntity<ItemEstoqueResponseDTO> buscar(@PathVariable Integer cdItemEstoque){
        var resp = itemEstoqueService.buscarItemEstoquePorId(cdItemEstoque);

        return ResponseEntity.ok(resp);
    }

    @PatchMapping("/{cdItemPedido}/adicionar-quantidade")
    public ResponseEntity<Void> adicionarQuantidade(@PathVariable Integer cdItemEstoque,
                                                    @RequestParam Integer qtde){
        itemEstoqueService.incrementarItemEstoque(cdItemEstoque, qtde);

        return ResponseEntity.status(200).build();

    }

    @PatchMapping("/{cdItemPedido}/diminuir-quantidade")
    public ResponseEntity<Void> diminuirQuantidade(@PathVariable Integer cdItemEstoque,
                                                   @RequestParam Integer qtde){
        itemEstoqueService.decrementarItemEstoque(cdItemEstoque, qtde);

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/delete/{cdItemEstoque}")
    public ResponseEntity<Void> deletarItem (@PathVariable Integer cdItemEstoque){
        itemEstoqueService.deletarItemEstoque(cdItemEstoque);

        return ResponseEntity.status(200).build();
    }


}
