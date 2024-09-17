package com.jciterceros.vr_online_backend.domain.dto.pessoa;

import com.jciterceros.vr_online_backend.domain.pessoa.models.SituacaoCPF;
import com.jciterceros.vr_online_backend.domain.pessoa.models.TipoPessoa;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
    private Long id;

    @NotNull(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Email é obrigatório")
    @Size(min = 3, max = 100, message = "Email deve ter entre 3 e 100 caracteres")
    private String email;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;

    @NotNull(message = "Situação é obrigatória")
    @Enumerated(EnumType.STRING)
    private SituacaoCPF situacao;

//    private List<ContatoDTO> contato;
}
