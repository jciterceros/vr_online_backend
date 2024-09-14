package com.jciterceros.vr_online_backend.domain.produtos.repositories;

import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
