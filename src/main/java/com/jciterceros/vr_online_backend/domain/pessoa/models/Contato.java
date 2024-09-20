package com.jciterceros.vr_online_backend.domain.pessoa.models;

import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_contato")
@Data
@NoArgsConstructor
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "contato")
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "contato")
    private List<Telefone> telefones;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
}
