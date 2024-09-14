package com.jciterceros.vr_online_backend.service;

import com.jciterceros.vr_online_backend.model.Produto;
import com.jciterceros.vr_online_backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Override
    public List<Produto> listAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto create(Produto produto) {
        if (produto.getId() != null) {
            throw new RuntimeException("Id must be null");
        }
        return produtoRepository.save(produto);
    }

    @Override
    public Produto update(Produto produto) {
        if (produto.getId() == null) {
            throw new RuntimeException("Id must not be null");
        }
        return produtoRepository.save(produto);
    }

    @Override
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}
