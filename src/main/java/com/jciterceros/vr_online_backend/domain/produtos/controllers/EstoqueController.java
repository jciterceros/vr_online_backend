package com.jciterceros.vr_online_backend.domain.produtos.controllers;

import com.jciterceros.vr_online_backend.domain.dto.produto.EstoqueDTO;
import com.jciterceros.vr_online_backend.domain.produtos.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @Autowired
    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> listarTodos() {
        List<EstoqueDTO> estoques = estoqueService.listarTodos();
        if (estoques.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDTO> buscarPorId(@PathVariable Long id) {
        Optional<EstoqueDTO> estoque = estoqueService.buscarPorId(id);
        return estoque.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstoqueDTO> salvar(@RequestBody EstoqueDTO estoqueDTO) {
        EstoqueDTO novoEstoque = estoqueService.salvar(estoqueDTO);
        return ResponseEntity.ok(novoEstoque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueDTO> atualizar(@PathVariable Long id, @RequestBody EstoqueDTO estoqueDTO) {
        EstoqueDTO estoqueAtualizado = estoqueService.atualizar(id, estoqueDTO);
        return ResponseEntity.ok(estoqueAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            estoqueService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}