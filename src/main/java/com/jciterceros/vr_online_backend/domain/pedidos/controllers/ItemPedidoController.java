package com.jciterceros.vr_online_backend.domain.pedidos.controllers;

import com.jciterceros.vr_online_backend.domain.dto.pedido.ItemPedidoDTO;
import com.jciterceros.vr_online_backend.domain.pedidos.services.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos/itens")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoDTO>> listarTodos() {
        List<ItemPedidoDTO> itensPedido = itemPedidoService.listarTodos();
        if (itensPedido.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itensPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> buscarPorId(@PathVariable Long id) {
        Optional<ItemPedidoDTO> itemPedido = itemPedidoService.buscarPorId(id);
        return itemPedido.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemPedidoDTO> salvar(@RequestBody ItemPedidoDTO itemPedidoDTO) {
        ItemPedidoDTO novoItemPedido = itemPedidoService.salvar(itemPedidoDTO);
        return ResponseEntity.ok(novoItemPedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> updateItemPedido(@PathVariable Long id, @RequestBody ItemPedidoDTO itemPedidoDTO) {
        ItemPedidoDTO itemPedidoAtualizado = itemPedidoService.atualizar(id, itemPedidoDTO);
        return ResponseEntity.ok(itemPedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        try {
            itemPedidoService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
