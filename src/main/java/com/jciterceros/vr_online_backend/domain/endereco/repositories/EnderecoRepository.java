package com.jciterceros.vr_online_backend.domain.endereco.repositories;

import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
