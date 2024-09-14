package com.jciterceros.vr_online_backend.domain.pessoa.models;

import com.jciterceros.vr_online_backend.domain.pessoa.services.ContatoService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipo;

    @Enumerated(EnumType.STRING)
    private SituacaoCPF situacao;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pessoa")
    private ContatoService contatoService;
}
