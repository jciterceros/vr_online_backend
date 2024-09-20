package com.jciterceros.vr_online_backend.domain.endereco.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_estado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "municipios")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String sigla;

    @OneToMany(mappedBy = "estado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Municipio> municipios;
}