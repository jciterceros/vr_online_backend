package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.dto.produto.EstoqueDTO;

import java.util.List;
import java.util.Optional;

public interface EstoqueService {
    String validateFields(EstoqueDTO estoqueDTO);

    List<EstoqueDTO> listarTodos();

    Optional<EstoqueDTO> buscarPorId(Long id);

    EstoqueDTO salvar(EstoqueDTO estoqueDTO);

    EstoqueDTO atualizar(Long id, EstoqueDTO estoqueDTO);

    void deletar(Long id);
}
