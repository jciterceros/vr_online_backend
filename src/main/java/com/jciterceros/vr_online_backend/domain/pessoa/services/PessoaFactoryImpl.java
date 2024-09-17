package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.PessoaDTO;
import com.jciterceros.vr_online_backend.domain.pessoa.exception.InvalidPessoaException;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaFisica;
import com.jciterceros.vr_online_backend.domain.pessoa.models.PessoaJuridica;
import com.jciterceros.vr_online_backend.domain.pessoa.models.SituacaoCNPJ;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PessoaFactoryImpl implements PessoaFactory {
    @Override
    public PessoaFisica createPessoaFisica(String cpf, String rg, LocalDate dataNascimento, String nomeSocial) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf(cpf);
        pessoaFisica.setRg(rg);
        pessoaFisica.setDataNascimento(dataNascimento);
        pessoaFisica.setNomeSocial(nomeSocial);
        return pessoaFisica;
    }

    @Override
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

    @Override
    public PessoaFisica createPessoaFisica() {
        return new PessoaFisica();
    }

    @Override
    public PessoaJuridica createPessoaJuridica() {
        return new PessoaJuridica();
    }

    @Override
    public Pessoa createPessoa(PessoaDTO pessoaDTO) {
        if (pessoaDTO.getTipo().equals("FISICA")) {
            PessoaFisica pessoaFisica = createPessoaFisica();
            //TODO: verify and set fields on pessoaFisica from pessoaDTO
            return pessoaFisica;
        }
        if (pessoaDTO.getTipo().equals("JURIDICA")) {
            PessoaJuridica pessoaJuridica = createPessoaJuridica();
            //TODO: verify set fields on pessoaJuridica from pessoaDTO
            return pessoaJuridica;
        }
        throw new InvalidPessoaException("Tipo de pessoa inválido: " + pessoaDTO.getTipo());
    }
}