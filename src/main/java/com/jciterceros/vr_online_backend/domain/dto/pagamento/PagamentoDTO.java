package com.jciterceros.vr_online_backend.domain.dto.pagamento;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.StatusPagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {
    private Long id;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Status é obrigatório")
    private StatusPagamento status;

    private LocalDate dataConfirmacao;

    @NotNull(message = "Tipo de pagamento é obrigatório")
    private TipoPagamento tipoPagamento;
}
