package com.jciterceros.vr_online_backend.domain.endereco.controllers;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.services.EnderecoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{cep}")
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

}
