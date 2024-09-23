package com.jciterceros.vr_online_backend.domain.pedidos.controllers;

import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoVendaDTO;
import com.jciterceros.vr_online_backend.domain.pedidos.services.PedidoVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos/vendas")
public class PedidoVendaController {

    private final PedidoVendaService pedidoVendaService;

    @Autowired
    public PedidoVendaController(PedidoVendaService pedidoVendaService) {
        this.pedidoVendaService = pedidoVendaService;
    }

    //    @PreAuthorize("hasRole('PEDIDO_VENDA_SELECT')")
    @GetMapping
    public ResponseEntity<List<PedidoVendaDTO>> listarTodos() {
        List<PedidoVendaDTO> pedidosVenda = pedidoVendaService.listarTodos();
        if (pedidosVenda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidosVenda);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoVendaDTO> buscarPorId(@PathVariable Long id) {
        Optional<PedidoVendaDTO> pedidoVenda = pedidoVendaService.buscarPorId(id);
        return pedidoVenda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //    @PreAuthorize("hasRole('PEDIDO_VENDA_INSERT')")
    @PostMapping
    public ResponseEntity<PedidoVendaDTO> salvar(@RequestBody PedidoVendaDTO pedidoVendaDTO) {
        PedidoVendaDTO novoPedidoVenda = pedidoVendaService.salvar(pedidoVendaDTO);
        return ResponseEntity.ok(novoPedidoVenda);
    }

    //    @PreAuthorize("hasRole('PEDIDO_VENDA_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoVendaDTO> updatePedidoVenda(@PathVariable Long id, @RequestBody PedidoVendaDTO pedidoVendaDTO) {
        PedidoVendaDTO pedidoVendaAtualizado = pedidoVendaService.atualizar(id, pedidoVendaDTO);
        return ResponseEntity.ok(pedidoVendaAtualizado);
    }

    //    @PreAuthorize("hasRole('PEDIDO_VENDA_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoVenda(@PathVariable Long id) {
        try {
            pedidoVendaService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}