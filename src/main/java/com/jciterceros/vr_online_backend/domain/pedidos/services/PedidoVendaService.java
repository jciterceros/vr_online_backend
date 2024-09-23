package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoVendaDTO;

import java.util.List;
import java.util.Optional;

public interface PedidoVendaService {
    String validateFields(PedidoVendaDTO pedidoVendaDTO);

    List<PedidoVendaDTO> listarTodos();

    Optional<PedidoVendaDTO> buscarPorId(Long id);

    PedidoVendaDTO salvar(PedidoVendaDTO pedidoVendaDTO);

    PedidoVendaDTO atualizar(Long id, PedidoVendaDTO pedidoVendaDTO);

    void deletar(Long id);
}