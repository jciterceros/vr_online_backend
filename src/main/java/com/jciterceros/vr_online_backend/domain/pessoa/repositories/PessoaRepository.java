package com.jciterceros.vr_online_backend.domain.pessoa.repositories;

import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
