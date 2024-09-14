package com.jciterceros.vr_online_backend.model;

import com.jciterceros.vr_online_backend.model.enums.Metrica;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private Metrica metrica;

    @Column(name = "valor_custo")
    private BigDecimal valorCusto;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda;
}
