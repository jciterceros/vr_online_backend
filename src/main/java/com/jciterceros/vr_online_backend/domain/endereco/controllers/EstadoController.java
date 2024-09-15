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

    // Endpoint para listar todos os estados
    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listarTodos() {
        List<EstadoDTO> estados = estadoService.listarTodos();
        if (estados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estados);
    }

    // Endpoint para buscar um estado por ID
    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscarPorId(@PathVariable Long id) {
        Optional<EstadoDTO> estado = estadoService.buscarPorId(id);
        return estado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para criar um novo estado
    @PostMapping
    public ResponseEntity<EstadoDTO> salvar(@RequestBody EstadoDTO estadoDTO) {
        EstadoDTO estadoCriado = estadoService.salvar(estadoDTO);

        if (estadoCriado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(estadoCriado);
    }

}
