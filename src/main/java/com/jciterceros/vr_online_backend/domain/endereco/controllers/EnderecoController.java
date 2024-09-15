package com.jciterceros.vr_online_backend.domain.endereco.controllers;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.services.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoServiceImpl enderecoService;

    @Autowired
    public EnderecoController(EnderecoServiceImpl enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
        List<EnderecoDTO> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Long id) {
        Optional<EnderecoDTO> endereco = enderecoService.buscarPorId(id);
        return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<EnderecoDTO> buscarEnderecoPorCep(@PathVariable String cep) {
        EnderecoDTO enderecoDTO = enderecoService.converterParaEndereco(cep, 10);
        return ResponseEntity.ok(enderecoDTO);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoBase = enderecoService.converterParaEndereco(enderecoDTO.getCep(), enderecoDTO.getNumero());
        EnderecoDTO enderecoSalvo = enderecoService.salvar(enderecoBase);
        if (enderecoSalvo == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(enderecoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {

        EnderecoDTO enderecoBase = enderecoService.converterParaEndereco(enderecoDTO.getCep(), enderecoDTO.getNumero());
        EnderecoDTO enderecoAtualizado = enderecoService.atualizar(id, enderecoBase);
        if (enderecoAtualizado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        try {
            enderecoService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
