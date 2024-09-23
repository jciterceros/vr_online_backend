package com.jciterceros.vr_online_backend.domain.pedidos.controllers;

import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoCompraDTO;
import com.jciterceros.vr_online_backend.domain.pedidos.services.PedidoCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos/compras")
public class PedidoCompraController {

    private final PedidoCompraService pedidoCompraService;

    @Autowired
    public PedidoCompraController(PedidoCompraService pedidoCompraService) {
        this.pedidoCompraService = pedidoCompraService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoCompraDTO>> listarTodos() {
        List<PedidoCompraDTO> pedidosCompra = pedidoCompraService.listarTodos();
        if (pedidosCompra.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidosCompra);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoCompraDTO> buscarPorId(@PathVariable Long id) {
        Optional<PedidoCompraDTO> pedidoCompra = pedidoCompraService.buscarPorId(id);
        return pedidoCompra.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoCompraDTO> salvar(@RequestBody PedidoCompraDTO pedidoCompraDTO) {
        PedidoCompraDTO novoPedidoCompra = pedidoCompraService.salvar(pedidoCompraDTO);
        return ResponseEntity.ok(novoPedidoCompra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoCompraDTO> updatePedidoCompra(@PathVariable Long id, @RequestBody PedidoCompraDTO pedidoCompraDTO) {
        PedidoCompraDTO pedidoCompraAtualizado = pedidoCompraService.atualizar(id, pedidoCompraDTO);
        return ResponseEntity.ok(pedidoCompraAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoCompra(@PathVariable Long id) {
        try {
            pedidoCompraService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
