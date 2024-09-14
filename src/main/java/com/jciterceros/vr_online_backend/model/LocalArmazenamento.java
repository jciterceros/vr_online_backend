package com.jciterceros.vr_online_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "local_armazenamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalArmazenamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double capacidadeTotal;
    private Double capacidadeDisponivel;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}
