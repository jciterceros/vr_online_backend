package com.jciterceros.vr_online_backend.domain.endereco.controllers;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.adapters.EnderecoViaCepAdapter;
import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.endereco.services.EnderecoServiceImpl;
import com.jciterceros.vr_online_backend.domain.endereco.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoServiceImpl enderecoService;
    private final ViaCepService viaCepService;
    private final EnderecoViaCepAdapter enderecoViaCepAdapter;

    @Autowired
    public EnderecoController(EnderecoServiceImpl enderecoService, ViaCepService viaCepService, EnderecoViaCepAdapter enderecoViaCepAdapter) {
        this.enderecoService = enderecoService;
        this.viaCepService = viaCepService;
        this.enderecoViaCepAdapter = enderecoViaCepAdapter;
    }

    // Endpoint para listar todos os endereços
    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
        List<EnderecoDTO> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }

}
