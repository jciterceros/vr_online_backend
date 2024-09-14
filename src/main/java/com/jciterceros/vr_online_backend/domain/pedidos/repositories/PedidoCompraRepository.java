package com.jciterceros.vr_online_backend.domain.pedidos.repositories;

import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Long> {
}
