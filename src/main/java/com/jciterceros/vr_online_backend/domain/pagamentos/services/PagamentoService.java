package com.jciterceros.vr_online_backend.domain.pagamentos.services;

import com.jciterceros.vr_online_backend.domain.dto.pagamento.PagamentoDTO;

import java.util.List;
import java.util.Optional;

public interface PagamentoService {
    String validateFields(PagamentoDTO pagamentoDTO);

    List<PagamentoDTO> listarTodos();

    Optional<PagamentoDTO> buscarPorId(Long id);

    PagamentoDTO salvar(PagamentoDTO pagamentoDTO);

    PagamentoDTO atualizar(Long id, PagamentoDTO pagamentoDTO);

    void deletar(Long id);

    // Novo método para processar o pagamento
    void processarPagamento(PagamentoDTO pagamentoDTO);
}