package com.jciterceros.vr_online_backend.domain.dto.pessoa;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {
    private Long id;

    private String fixo;

    @NotNull(message = "Celular é obrigatório")
    private String celular;

    private String comercial;
    
    private String principal;
}
