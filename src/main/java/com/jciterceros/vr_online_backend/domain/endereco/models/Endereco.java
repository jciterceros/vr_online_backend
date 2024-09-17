package com.jciterceros.vr_online_backend.domain.endereco.models;

import com.jciterceros.vr_online_backend.domain.pessoa.models.Contato;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Endereco implements IEndereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;
    private String bairro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contato_id")
    private Contato contato;
}
