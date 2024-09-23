package com.jciterceros.vr_online_backend.domain.pagamentos.controllers;

import com.jciterceros.vr_online_backend.domain.dto.pagamento.PagamentoDTO;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PreAuthorize("hasRole('PAGAMENTO_SELECT')")
    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> listarTodos() {
        List<PagamentoDTO> pagamentos = pagamentoService.listarTodos();
        if (pagamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPorId(@PathVariable Long id) {
        Optional<PagamentoDTO> pagamento = pagamentoService.buscarPorId(id);
        return pagamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('PAGAMENTO_INSERT')")
    @PostMapping
    public ResponseEntity<PagamentoDTO> salvar(@RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO novoPagamento = pagamentoService.salvar(pagamentoDTO);
        return ResponseEntity.ok(novoPagamento);
    }

    @PreAuthorize("hasRole('PAGAMENTO_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> updatePagamento(@PathVariable Long id, @RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamentoAtualizado = pagamentoService.atualizar(id, pagamentoDTO);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @PreAuthorize("hasRole('PAGAMENTO_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        try {
            pagamentoService.deletar(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}