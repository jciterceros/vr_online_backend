package com.jciterceros.vr_online_backend.domain.pessoa.repositories;

import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long>{
}
