package com.jciterceros.vr_online_backend.domain.pessoa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_telefone")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fixo;
    private String celular;
    private String comercial;
    private String principal;

    @ManyToOne
    @JoinColumn(name = "contato_service_id")
    private ContatoService contatoService;
}
