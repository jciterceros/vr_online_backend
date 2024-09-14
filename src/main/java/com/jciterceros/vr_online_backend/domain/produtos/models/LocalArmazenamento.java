package com.jciterceros.vr_online_backend.domain.produtos.models;

import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

//    @OneToOne
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(name = "capacidade_total")
    private BigDecimal capacidadeTotal;

    @Column(name = "capacidade_disponivel")
    private BigDecimal capacidadeDisponivel;
}
