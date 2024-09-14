package com.jciterceros.vr_online_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "local_armazenamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "contato_service_id")
    private List<Endereco> enderecos;

    @OneToMany
    @JoinColumn(name = "contato_service_id")
    private List<Telefone> telefones;
}
