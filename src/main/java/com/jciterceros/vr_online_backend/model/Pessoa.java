package com.jciterceros.vr_online_backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa {
    private UUID id;
    private String nome;
//    private TipoPessoa tipo;
//    private ContatoService contatoService;
    private String email;
//    private SituacaoCPF situacao;
}
