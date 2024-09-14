package com.jciterceros.vr_online_backend.domain.pedidos.models;

import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "pedido_venda")
@Data
@NoArgsConstructor
public class PedidoVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Pessoa cliente;

    private String cpfNota;

    @OneToMany(mappedBy = "pedidoVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    private BigDecimal valorTotal;
    private Date dataPedido;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    private Date dataEntrega;

    @ManyToOne
    @JoinColumn(name = "local_entrega_id")
    private Endereco localEntrega;
}
