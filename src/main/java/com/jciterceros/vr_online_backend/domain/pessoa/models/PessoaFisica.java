package com.jciterceros.vr_online_backend.domain.pessoa.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PessoaFisica extends Pessoa {
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
    private String nomeSocial;
}
