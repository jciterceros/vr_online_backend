package com.jciterceros.vr_online_backend.domain.dto.pessoa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridicaDTO {

    @NotNull(message = "CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "CNPJ deve ter 14 caracteres")
    private String cnpj;

    @NotNull(message = "Inscrição Estadual é obrigatória")
    @Size(min = 9, max = 9, message = "Inscrição Estadual deve ter 9 caracteres")
    private String inscricaoEstadual;

    @NotNull(message = "Inscrição Municipal é obrigatória")
    @Size(min = 8, max = 8, message = "Inscrição Municipal deve ter 8 caracteres")
    private String inscricaoMunicipal;

    @NotNull(message = "Razão Social é obrigatória")
    @Size(min = 3, max = 100, message = "Razão Social deve ter entre 3 e 100 caracteres")
    private String ramoAtividade;

    @NotNull(message = "Ramo de Atividade é obrigatório")
    @Size(min = 3, max = 100, message = "Ramo de Atividade deve ter entre 3 e 100 caracteres")
    private String razaoSocial;

    @NotNull(message = "Situação Cadastral é obrigatória")
    @Size(min = 5, max = 7, message = "Situação Cadastral deve ter entre 5 e 7 caracteres")
    private String situacaoCadastral;  // ATIVO ou INATIVO
}
