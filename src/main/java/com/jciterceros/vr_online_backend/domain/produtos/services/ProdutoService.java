package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.dto.produto.ProdutoDTO;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    String validateFields(ProdutoDTO productDTO);

    List<ProdutoDTO> listarTodos();

    Optional<ProdutoDTO> buscarPorId(Long id);

    ProdutoDTO salvar(ProdutoDTO productDTO);

    ProdutoDTO atualizar(Long id, ProdutoDTO productDTO);

    void deletar(Long id);
//    List<Produto> listAll();
//    Produto create(Produto product);
//    Produto update(Produto product);
//    void delete(Long id);
}
