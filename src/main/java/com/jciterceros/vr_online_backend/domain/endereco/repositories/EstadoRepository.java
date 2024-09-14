package com.jciterceros.vr_online_backend.domain.endereco.repositories;

import com.jciterceros.vr_online_backend.domain.endereco.models.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
