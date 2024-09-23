package com.jciterceros.vr_online_backend.domain.dto.pedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    private Long id;

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    private BigDecimal quantidade;

    private BigDecimal valorAtualProduto;

    private LocalDate dataPedido;

    private BigDecimal subTotal;

    private Long pedidoCompraId;

    private Long pedidoVendaId;
}