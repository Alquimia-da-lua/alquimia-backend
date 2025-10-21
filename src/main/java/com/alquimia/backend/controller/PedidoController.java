package com.alquimia.backend.controller;

import com.alquimia.backend.dto.request.PedidoRequestDTO;
import com.alquimia.backend.dto.response.PedidoResponseDTO;
import com.alquimia.backend.enums.StatusPedido;
import com.alquimia.backend.enums.TipoPagamento;
import com.alquimia.backend.model.Pedido;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> cadastrarPedido(@RequestBody @Valid PedidoRequestDTO pedidoDTO) {
        Pedido novoPedido = this.pedidoService.cadastrarPedido(pedidoDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoPedido);
    }

    @GetMapping("/buscar/{cdPedido}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable("cdPedido") Integer cdPedido) {
        Pedido pedido = pedidoService.buscarPedidoCd(cdPedido);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listarTodosPedidos() {
        var lista = pedidoService.listarPedidos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/listar/cliente/{cdUsuario}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorCliente(@PathVariable("cdUsuario") Usuario cdUsuario) {
        var lista = pedidoService.listarPedidosPorCliente(cdUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @PutMapping("/status/{cdPedido}")
    public ResponseEntity<Pedido> alterarStatusPedido(@PathVariable("cdPedido") Integer cdPedido,
                                                      @RequestParam("novoStatus") StatusPedido statusPedido) {
        Pedido pedidoAtualizado = pedidoService.alterarStatusPedido(cdPedido, statusPedido);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoAtualizado);
    }

    @PutMapping("/pagamento/{cdPedido}")
    public ResponseEntity<Pedido> alterarTipoPagamento(@PathVariable("cdPedido") Integer cdPedido,
                                                       @RequestParam("novoTipo") TipoPagamento novoTipo) {
        Pedido pedidoAtualizado = pedidoService.alterarTipoPagamento(cdPedido, novoTipo);
        return ResponseEntity.status(HttpStatus.OK).body(pedidoAtualizado);
    }
}
