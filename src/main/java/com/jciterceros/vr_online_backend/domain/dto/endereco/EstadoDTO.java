package com.jciterceros.vr_online_backend.domain.dto.endereco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
    private Long id;

    @NotNull(message = "Descrição é obrigatória")
    @Size(min = 3, max = 100, message = "Descrição deve ter entre 3 e 100 caracteres")
    private String descricao;

    @NotNull(message = "Sigla é obrigatória")
    @Size(min = 2, max = 2, message = "Sigla deve ter 2 caracteres")
    private String sigla;
}