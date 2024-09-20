package com.jciterceros.vr_online_backend.domain.pessoa.controllers;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.ContatoDTO;
import com.jciterceros.vr_online_backend.domain.pessoa.services.ContatoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contato")
public class ContatoController {

    private final ContatoServiceImpl contatoService;

    @Autowired
    public ContatoController(ContatoServiceImpl contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listarTodos() {
        List<ContatoDTO> contatos = contatoService.listarTodos();
        if (contatos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> buscarPorId(@PathVariable Long id) {
        Optional<ContatoDTO> contato = contatoService.buscarPorId(id);
        return contato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContatoDTO> salvar(@RequestBody ContatoDTO contatoDTO) {
        ContatoDTO novoContato = contatoService.salvar(contatoDTO);
        return ResponseEntity.ok(novoContato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> atualizar(@PathVariable Long id, @RequestBody ContatoDTO contatoDTO) {
        ContatoDTO contatoAtualizado = contatoService.atualizar(id, contatoDTO);
        return ResponseEntity.ok(contatoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            contatoService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
