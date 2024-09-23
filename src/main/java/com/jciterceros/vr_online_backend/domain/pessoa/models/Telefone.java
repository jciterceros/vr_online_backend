package com.jciterceros.vr_online_backend.domain.pessoa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_telefone")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fixo;
    private String celular;
    private String comercial;
    private String principal;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_id")
    private Contato contato;
}
