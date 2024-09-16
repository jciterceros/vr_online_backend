package com.jciterceros.vr_online_backend.domain.produtos.controllers;

import com.jciterceros.vr_online_backend.domain.dto.produto.LocalArmazenamentoDTO;
import com.jciterceros.vr_online_backend.domain.produtos.services.LocalArmazenamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/localarmazenamento")
public class LocalArmazenamentoController {

    private final LocalArmazenamentoService localArmazenamentoService;

    @Autowired
    public LocalArmazenamentoController(LocalArmazenamentoService localArmazenamentoService) {
        this.localArmazenamentoService = localArmazenamentoService;
    }

    @GetMapping
    public ResponseEntity<List<LocalArmazenamentoDTO>> listarTodos() {
        List<LocalArmazenamentoDTO> localArmazenamentos = localArmazenamentoService.listarTodos();
        if (localArmazenamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(localArmazenamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalArmazenamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<LocalArmazenamentoDTO> localArmazenamento = localArmazenamentoService.buscarPorId(id);
        return localArmazenamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LocalArmazenamentoDTO> salvar(@RequestBody LocalArmazenamentoDTO localArmazenamentoDTO) {
        LocalArmazenamentoDTO novoLocalArmazenamento = localArmazenamentoService.salvar(localArmazenamentoDTO);
        return ResponseEntity.ok(novoLocalArmazenamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalArmazenamentoDTO> atualizar(@PathVariable Long id, @RequestBody LocalArmazenamentoDTO localArmazenamentoDTO) {
        LocalArmazenamentoDTO localArmazenamentoAtualizado = localArmazenamentoService.atualizar(id, localArmazenamentoDTO);
        return ResponseEntity.ok(localArmazenamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            localArmazenamentoService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}