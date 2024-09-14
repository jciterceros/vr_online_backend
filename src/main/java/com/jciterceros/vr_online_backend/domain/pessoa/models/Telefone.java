package com.jciterceros.vr_online_backend.domain.pessoa.models;

import com.jciterceros.vr_online_backend.domain.pessoa.services.ContatoService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "telefone")
@Getter
@Setter
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
