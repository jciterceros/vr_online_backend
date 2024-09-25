package com.jciterceros.vr_online_backend.domain.dto.pedido;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.enums.TipoPagamento;
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
public class PedidoVendaDTO {
    private Long id;

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "CPF da Nota é obrigatório")
    private String cpfNota;

    private List<Long> itensIds;

    private BigDecimal valorTotal;

    private LocalDate dataPedido;

    @NotNull(message = "Tipo de pagamento é obrigatório")
    private TipoPagamento tipoPagamento;

    @NotNull(message = "Pagamento é obrigatório")
    private Long pagamentoId;

    private LocalDate dataEntrega;

    @NotNull(message = "Local de entrega é obrigatório")
    private Long localEntregaId;
}