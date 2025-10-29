package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.ItemPedidoRequestDTO;
import com.alquimia.backend.dto.response.ItemPedidoResponseDTO;
import com.alquimia.backend.model.ItemPedido;
import com.alquimia.backend.service.ItemPedidoService;
import com.alquimia.backend.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/itempedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;
    private final PedidoService pedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService, PedidoService pedidoService) {
        this.itemPedidoService = itemPedidoService;
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<ItemPedidoResponseDTO> cadastrarItemPedido(@RequestBody @Valid ItemPedidoRequestDTO itemPedidoDTO) {

        ItemPedidoResponseDTO response = this.itemPedidoService.cadastrarItemPedido(itemPedidoDTO);
        pedidoService.atualizarValorPedido(response.cdPedido());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ItemPedidoResponseDTO>> listarItensPedido() {

        var lista = itemPedidoService.listarTodosItensPedido();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @DeleteMapping("/delete/{cdItemPedido}")
    public ResponseEntity<Void> removerItemPedido(@PathVariable("cdItemPedido") Integer cdItemPedido) {

        Integer cdPedidoAfetado = itemPedidoService.removerItemDoPedido(cdItemPedido);
        pedidoService.atualizarValorPedido(cdPedidoAfetado);
        return ResponseEntity.status(200).build();
    }

}
