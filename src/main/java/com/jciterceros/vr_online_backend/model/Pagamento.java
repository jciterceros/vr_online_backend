package com.jciterceros.vr_online_backend.model;

import com.jciterceros.vr_online_backend.model.enums.StatusPagamento;
import com.jciterceros.vr_online_backend.model.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pagamento")
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
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento status;

    @Temporal(TemporalType.DATE)
    private Date dataConfirmacao;

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
        if (processar != null) {
            processar.processarPagamento(this);
        }
    }
}
