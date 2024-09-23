package com.jciterceros.vr_online_backend.domain.produtos.models;

import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_local_armazenamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocalArmazenamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(name = "capacidade_total")
    private BigDecimal capacidadeTotal;

    @Column(name = "capacidade_disponivel")
    private BigDecimal capacidadeDisponivel;
}
