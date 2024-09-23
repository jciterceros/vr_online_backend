package com.jciterceros.vr_online_backend.domain.dto.pedido;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCompraDTO {
    private Long id;

    @NotNull(message = "Comprador é obrigatório")
    private Long compradorId;

    @NotNull(message = "Fornecedor é obrigatório")
    private Long fornecedorId;

    private List<Long> itensIds;

    private BigDecimal valorTotal;

    private LocalDate dataPedido;

    @NotNull(message = "Pagamento é obrigatório")
    private Long pagamentoId;

    private LocalDate dataEntrega;

    @NotNull(message = "Local de entrega é obrigatório")
    private Long localEntregaId;
}