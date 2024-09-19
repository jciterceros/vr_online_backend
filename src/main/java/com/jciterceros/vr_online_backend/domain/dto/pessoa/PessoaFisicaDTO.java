package com.jciterceros.vr_online_backend.domain.dto.pessoa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaDTO {

    @NotNull(message = "Data de Nascimento é obrigatória")
    @Size(min = 10, max = 10, message = "Data de Nascimento deve ter 10 caracteres")
    private String dataNascimento;

    @NotNull(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    private String nomeSocial;

    @NotNull(message = "RG é obrigatório")
    @Size(min = 9, max = 9, message = "RG deve ter 9 caracteres")
    private String rg;
}
