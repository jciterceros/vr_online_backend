package com.jciterceros.vr_online_backend.domain.endereco.controllers;

import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;
import com.jciterceros.vr_online_backend.domain.endereco.services.ViaCepServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cep")
public class ViaCepController {

    private final ViaCepServiceImpl viaCepService;

    @Autowired
    public ViaCepController(ViaCepServiceImpl viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepDTO> buscarEnderecoPorCep(@PathVariable String cep) {
        ViaCepDTO viaCepDTO = viaCepService.buscarEnderecoPorCep(cep);
        return ResponseEntity.ok(viaCepDTO);
    }
}
