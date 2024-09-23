package com.jciterceros.vr_online_backend.domain.endereco.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_municipio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

}
