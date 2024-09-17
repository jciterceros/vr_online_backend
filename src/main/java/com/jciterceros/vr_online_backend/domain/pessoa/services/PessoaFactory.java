package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.PessoaDTO;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaFisica;
import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaJuridica;
import com.jciterceros.vr_online_backend.domain.pessoa.models.SituacaoCNPJ;

import java.time.LocalDate;

public interface PessoaFactory {
    PessoaFisica createPessoaFisica(String cpf, String rg, LocalDate dataNascimento, String nomeSocial);

    PessoaJuridica createPessoaJuridica(String cnpj, String inscricaoEstadual, String inscricaoMunicipal,
                                        String razaoSocial, String ramoAtividade, SituacaoCNPJ situacaoCadastral);

    PessoaFisica createPessoaFisica();

    PessoaJuridica createPessoaJuridica();

    Pessoa createPessoa(PessoaDTO pessoaDTO);
}