package com.jciterceros.vr_online_backend.domain.pessoa.factories;

import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaFisica;
import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaJuridica;
import com.jciterceros.vr_online_backend.domain.pessoa.models.SituacaoCNPJ;

import java.util.Date;

public class PessoaFactory {
    public PessoaFisica createPessoaFisica(String cpf, String rg, Date dataNascimento, String nomeSocial) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setRg(rg);
        pessoaFisica.setDataNascimento(dataNascimento);
        pessoaFisica.setNomeSocial(nomeSocial);
        return pessoaFisica;
    }

    public PessoaJuridica createPessoaJuridica(String cnpj, String inscricaoEstadual, String inscricaoMunicipal,
                                               String razaoSocial, String ramoAtividade, SituacaoCNPJ situacaoCadastral) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj(cnpj);
        pessoaJuridica.setInscricaoEstadual(inscricaoEstadual);
        pessoaJuridica.setInscricaoMunicipal(inscricaoMunicipal);
        pessoaJuridica.setRazaoSocial(razaoSocial);
        pessoaJuridica.setRamoAtividade(ramoAtividade);
        pessoaJuridica.setSituacaoCadastral(situacaoCadastral);
        return pessoaJuridica;
    }

    public PessoaFisica createPessoaFisica() {
        return new PessoaFisica();
    }

    public PessoaJuridica createPessoaJuridica() {
        return new PessoaJuridica();
    }
}
