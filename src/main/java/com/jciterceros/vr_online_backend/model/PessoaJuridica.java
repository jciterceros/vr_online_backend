package com.jciterceros.vr_online_backend.model;

import com.jciterceros.vr_online_backend.model.enums.SituacaoCNPJ;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa_juridica")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaJuridica extends Pessoa {
    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String razaoSocial;
    private String ramoAtividade;

    @Enumerated(EnumType.STRING)
    private SituacaoCNPJ situacaoCadastral;
}
