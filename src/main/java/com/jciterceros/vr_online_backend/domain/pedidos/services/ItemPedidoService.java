package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.ItemPedidoDTO;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoService {
    String validateFields(ItemPedidoDTO itemPedidoDTO);

    List<ItemPedidoDTO> listarTodos();

    Optional<ItemPedidoDTO> buscarPorId(Long id);

    ItemPedidoDTO salvar(ItemPedidoDTO itemPedidoDTO);

    ItemPedidoDTO atualizar(Long id, ItemPedidoDTO itemPedidoDTO);

    ItemPedidoDTO adicionarPedidoCompra(Long id, Long pedidoCompraId);

    ItemPedidoDTO adicionarPedidoVenda(Long id, Long pedidoVendaId);

    ItemPedidoDTO removerPedidoCompra(Long id);

    ItemPedidoDTO removerPedidoVenda(Long id);

    void deletar(Long id);
}
