package com.jciterceros.vr_online_backend.domain.pessoa.controllers;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.TelefoneDTO;
import com.jciterceros.vr_online_backend.domain.pessoa.services.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/telefone")
public class TelefoneController {

        private final TelefoneService telefoneService;

        @Autowired
        public TelefoneController(TelefoneService telefoneService) {
            this.telefoneService = telefoneService;
        }

        @GetMapping
        public ResponseEntity<List<TelefoneDTO>> listarTodos() {
            List<TelefoneDTO> telefones = telefoneService.listarTodos();
            if (telefones.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(telefones);
        }

        @GetMapping("/{id}")
        public ResponseEntity<TelefoneDTO> buscarPorId(@PathVariable Long id) {
            Optional<TelefoneDTO> telefone = telefoneService.buscarPorId(id);
            return telefone.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<TelefoneDTO> salvar(@RequestBody TelefoneDTO telefoneDTO) {
            TelefoneDTO novoTelefone = telefoneService.salvar(telefoneDTO);
            return ResponseEntity.ok(novoTelefone);
        }

        @PutMapping("/{id}")
        public ResponseEntity<TelefoneDTO> atualizar(@PathVariable Long id, @RequestBody TelefoneDTO telefoneDTO) {
            TelefoneDTO telefoneAtualizado = telefoneService.atualizar(id, telefoneDTO);
            return ResponseEntity.ok(telefoneAtualizado);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletar(@PathVariable Long id) {
            try {
                telefoneService.deletar(id);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.noContent().build();
        }
}
