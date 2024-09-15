package com.jciterceros.vr_online_backend.domain.dto.produto;

import com.jciterceros.vr_online_backend.domain.produtos.models.Metrica;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;

    @NotNull(message = "Marca é obrigatória")
    @Size(min = 3, max = 100, message = "Marca deve ter entre 3 e 100 caracteres")
    private String marca;

    @NotNull(message = "Modelo é obrigatório")
    @Size(min = 3, max = 100, message = "Modelo deve ter entre 3 e 100 caracteres")
    private String modelo;

    @NotNull(message = "Descrição é obrigatória")
    @Size(min = 3, max = 100, message = "Descrição deve ter entre 3 e 100 caracteres")
    private String descricao;

    @NotNull(message = "Métrica é obrigatória")
    @Enumerated(EnumType.STRING)
    private Metrica metrica;

    @NotNull(message = "Valor de custo é obrigatório")
    private BigDecimal valorCusto;

    @NotNull(message = "Valor de venda é obrigatório")
    private BigDecimal valorVenda;
}
