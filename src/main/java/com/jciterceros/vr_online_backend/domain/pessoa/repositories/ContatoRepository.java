package com.jciterceros.vr_online_backend.domain.pessoa.repositories;

import com.jciterceros.vr_online_backend.domain.pessoa.models.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>{
}
