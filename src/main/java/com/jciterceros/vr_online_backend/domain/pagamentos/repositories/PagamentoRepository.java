package com.jciterceros.vr_online_backend.domain.pagamentos.repositories;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
