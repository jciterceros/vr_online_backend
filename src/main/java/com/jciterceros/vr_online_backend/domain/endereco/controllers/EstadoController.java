package com.jciterceros.vr_online_backend.domain.endereco.controllers;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EstadoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.services.EstadoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {
    private final EstadoServiceImpl estadoService;

    @Autowired
    public EstadoController(EstadoServiceImpl estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listarTodos() {
        List<EstadoDTO> estados = estadoService.listarTodos();
        if (estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscarPorId(@PathVariable Long id) {
        Optional<EstadoDTO> estado = estadoService.buscarPorId(id);
        return estado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> salvar(@RequestBody EstadoDTO estadoDTO) {
        EstadoDTO estadoCriado = estadoService.salvar(estadoDTO);

        if (estadoCriado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(estadoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> updateEstado(@PathVariable Long id, @RequestBody EstadoDTO estadoDTO) {
        EstadoDTO estadoAtualizado = estadoService.atualizar(id, estadoDTO);
        return ResponseEntity.ok(estadoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        try {
            estadoService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
