package com.jciterceros.vr_online_backend.domain.pedidos.repositories;

import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoVendaRepository extends JpaRepository<PedidoVenda, Long> {
}
