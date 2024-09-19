package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.PessoaDTO;
import com.jciterceros.vr_online_backend.domain.pessoa.exception.InvalidPessoaException;
import com.jciterceros.vr_online_backend.domain.pessoa.models.*;
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
        if (pessoaDTO.getTipo().equals(TipoPessoa.FISICA)) {
            PessoaFisica pessoaFisica = createPessoaFisica();

            pessoaFisica.setNome(pessoaDTO.getNome());
            pessoaFisica.setEmail(pessoaDTO.getEmail());
            pessoaFisica.setTipo(pessoaDTO.getTipo());
            pessoaFisica.setSituacao(pessoaDTO.getSituacao());

            pessoaFisica.setCpf(pessoaDTO.getPessoaFisica().getCpf());
            pessoaFisica.setRg(pessoaDTO.getPessoaFisica().getRg());
            pessoaFisica.setDataNascimento(LocalDate.parse(pessoaDTO.getPessoaFisica().getDataNascimento()));
            pessoaFisica.setNomeSocial(pessoaDTO.getPessoaFisica().getNomeSocial());

            return pessoaFisica;
        } else if (pessoaDTO.getTipo().equals(TipoPessoa.JURIDICA)) {
            PessoaJuridica pessoaJuridica = createPessoaJuridica();

            pessoaJuridica.setNome(pessoaDTO.getNome());
            pessoaJuridica.setEmail(pessoaDTO.getEmail());
            pessoaJuridica.setTipo(pessoaDTO.getTipo());
            pessoaJuridica.setSituacao(pessoaDTO.getSituacao());

            pessoaJuridica.setCnpj(pessoaDTO.getPessoaJuridica().getCnpj());
            pessoaJuridica.setInscricaoEstadual(pessoaDTO.getPessoaJuridica().getInscricaoEstadual());
            pessoaJuridica.setInscricaoMunicipal(pessoaDTO.getPessoaJuridica().getInscricaoMunicipal());
            pessoaJuridica.setRazaoSocial(pessoaDTO.getPessoaJuridica().getRazaoSocial());
            pessoaJuridica.setRamoAtividade(pessoaDTO.getPessoaJuridica().getRamoAtividade());
            pessoaJuridica.setSituacaoCadastral(SituacaoCNPJ.valueOf(pessoaDTO.getPessoaJuridica().getSituacaoCadastral()));

            return pessoaJuridica;
        } else {
            throw new InvalidPessoaException("Tipo de pessoa inválido: " + pessoaDTO.getTipo());
        }
    }
}