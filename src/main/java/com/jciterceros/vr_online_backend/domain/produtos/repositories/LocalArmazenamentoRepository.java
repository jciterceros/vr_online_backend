package com.jciterceros.vr_online_backend.domain.produtos.repositories;

import com.jciterceros.vr_online_backend.domain.produtos.models.LocalArmazenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalArmazenamentoRepository extends JpaRepository<LocalArmazenamento, Long> {
}
