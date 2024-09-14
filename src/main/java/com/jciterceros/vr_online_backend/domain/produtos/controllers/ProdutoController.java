package com.jciterceros.vr_online_backend.domain.produtos.controllers;

import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;
import com.jciterceros.vr_online_backend.domain.produtos.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PreAuthorize("hasRole('PRODUCT_SELECT')")
    @GetMapping
    public List<Produto> listAll() {
        return produtoService.listAll();
    }

    @PreAuthorize("hasRole('PRODUCT_INSERT')")
    @PostMapping
    public Produto create(@RequestBody Produto produto) {
        return produtoService.create(produto);
    }

    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @PutMapping
    public Produto update(@RequestBody Produto produto) {
        return produtoService.update(produto);
    }

    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produtoService.delete(id);
    }
}
