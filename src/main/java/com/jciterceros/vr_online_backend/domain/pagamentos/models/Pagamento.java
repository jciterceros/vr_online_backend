package com.jciterceros.vr_online_backend.domain.pagamentos.models;

import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoCompra;
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoVenda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_pagamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Temporal(TemporalType.DATE)
    private LocalDate dataConfirmacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPagamento tipoPagamento;

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PedidoVenda pedidoVenda;

    @OneToOne(mappedBy = "pagamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PedidoCompra pedidoCompra;

    @Transient
    private IPagamentoProcessar processar;

    @Transient
    private IPagamentoValidar validar;

    @Transient
    private IPagamentoNotificar notificar;

    public void processar() {
        if (validar.validarPagamento(this)) {
            processar.processarPagamento(this);
            this.status = StatusPagamento.CONFIRMADO;
            this.dataConfirmacao = LocalDate.now();
            notificar.notificarStatus(this);
        } else {
            this.status = StatusPagamento.CANCELADO;
            System.out.println("Pagamento inválido.");
        }
    }
}
