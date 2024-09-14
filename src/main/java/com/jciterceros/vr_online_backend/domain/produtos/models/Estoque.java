package com.jciterceros.vr_online_backend.domain.produtos.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    private Metrica tipoMedida;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "local_armazenamento_id")
    private LocalArmazenamento localArmazenamento;
}
