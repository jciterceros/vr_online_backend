package com.jciterceros.vr_online_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PessoaFisica extends Pessoa {
    private String cpf;
    private String rg;
    private Date dataNascimento;
    private String nomeSocial;
}
