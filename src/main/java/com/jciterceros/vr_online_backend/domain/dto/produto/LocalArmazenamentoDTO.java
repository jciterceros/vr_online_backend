package com.jciterceros.vr_online_backend.domain.dto.produto;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalArmazenamentoDTO {
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    //    @JsonIgnore
    @NotNull(message = "Endereço é obrigatório")
    private EnderecoDTO endereco;

    @NotNull(message = "Capacidade total é obrigatória")
    private BigDecimal capacidadeTotal;

    @NotNull(message = "Capacidade disponível é obrigatória")
    private BigDecimal capacidadeDisponivel;
}
