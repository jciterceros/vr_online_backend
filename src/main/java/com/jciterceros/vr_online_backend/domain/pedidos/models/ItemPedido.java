package com.jciterceros.vr_online_backend.domain.pedidos.models;

import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_item_pedido")
@ToString
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private BigDecimal quantidade;
    private BigDecimal valorAtualProduto;
    private LocalDate dataPedido;
    private BigDecimal subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_compra_id")
    private PedidoCompra pedidoCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_venda_id")
    private PedidoVenda pedidoVenda;
}
