package com.jciterceros.vr_online_backend.domain.pedidos.models;

import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_pedido_compra")
@Data
@NoArgsConstructor
public class PedidoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprador_id", nullable = false)
    private Pessoa comprador;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Pessoa fornecedor;

    @OneToMany(mappedBy = "pedidoCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    private BigDecimal valorTotal;
    private LocalDate dataPedido;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    private LocalDate dataEntrega;

    @ManyToOne
    @JoinColumn(name = "local_entrega_id")
    private Endereco localEntrega;
}
