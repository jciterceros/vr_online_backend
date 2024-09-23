package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoCompraDTO;

import java.util.List;
import java.util.Optional;

public interface PedidoCompraService {
    String validateFields(PedidoCompraDTO pedidoCompraDTO);

    List<PedidoCompraDTO> listarTodos();

    Optional<PedidoCompraDTO> buscarPorId(Long id);

    PedidoCompraDTO salvar(PedidoCompraDTO pedidoCompraDTO);

    PedidoCompraDTO atualizar(Long id, PedidoCompraDTO pedidoCompraDTO);

    void deletar(Long id);
}