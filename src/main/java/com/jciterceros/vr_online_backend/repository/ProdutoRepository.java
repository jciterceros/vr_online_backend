package com.jciterceros.vr_online_backend.repository;

import com.jciterceros.vr_online_backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
