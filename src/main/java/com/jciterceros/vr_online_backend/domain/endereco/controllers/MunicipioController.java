package com.jciterceros.vr_online_backend.domain.endereco.controllers;

import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.endereco.services.MunicipioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/municipios")
public class MunicipioController {
    private final MunicipioServiceImpl municipioService;

    @Autowired
    public MunicipioController(MunicipioServiceImpl municipioService) {
        this.municipioService = municipioService;
    }

    @GetMapping
    public ResponseEntity<List<MunicipioDTO>> listarTodos() {
        List<MunicipioDTO> municipios = municipioService.listarTodos();
        if (municipios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(municipios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipioDTO> buscarPorId(@PathVariable Long id) {
        Optional<MunicipioDTO> municipio = municipioService.buscarPorId(id);
        return municipio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MunicipioDTO> salvar(@RequestBody MunicipioDTO municipioDTO) {
        MunicipioDTO municipioCriado = municipioService.salvar(municipioDTO);

        if (municipioCriado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(municipioCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MunicipioDTO> updateMunicipio(@PathVariable Long id, @RequestBody MunicipioDTO municipioDTO) {
        MunicipioDTO updatedMunicipio = municipioService.atualizar(id, municipioDTO);
        return ResponseEntity.ok(updatedMunicipio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMunicipio(@PathVariable Long id) {
        try {
            municipioService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
