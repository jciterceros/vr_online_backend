package com.jciterceros.vr_online_backend.domain.dto.produto;

import com.jciterceros.vr_online_backend.domain.produtos.models.LocalArmazenamento;
import com.jciterceros.vr_online_backend.domain.produtos.models.Metrica;
import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Long id;

    @NotNull(message = "Quantidade é obrigatória")
    private BigDecimal quantidade;

    @NotNull(message = "Métrica é obrigatória")
    @Enumerated(EnumType.STRING)
    private Metrica tipoMedida;

    @NotNull(message = "Produto é obrigatório")
    private Produto produto;

    @NotNull(message = "Local de armazenamento é obrigatório")
    private LocalArmazenamento localArmazenamento;
}