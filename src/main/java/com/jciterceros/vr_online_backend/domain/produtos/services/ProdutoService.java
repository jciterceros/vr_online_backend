package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;

import java.util.List;

public interface ProdutoService {
    List<Produto> listAll();

    Produto create(Produto product);

    Produto update(Produto product);

    void delete(Long id);
}
