package com.jciterceros.vr_online_backend.domain.endereco.models;

import com.jciterceros.vr_online_backend.domain.pessoa.models.Contato;
import com.jciterceros.vr_online_backend.domain.produtos.models.LocalArmazenamento;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @OneToMany(mappedBy = "endereco", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LocalArmazenamento> locaisArmazenamento;
}
