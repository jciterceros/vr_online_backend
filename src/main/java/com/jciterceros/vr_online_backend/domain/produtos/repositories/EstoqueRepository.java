package com.jciterceros.vr_online_backend.domain.produtos.repositories;

import com.jciterceros.vr_online_backend.domain.produtos.models.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
