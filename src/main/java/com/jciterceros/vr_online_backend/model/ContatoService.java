package com.jciterceros.vr_online_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "contato_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "contato_service_id")
    private List<Endereco> enderecos;

    @OneToMany
    @JoinColumn(name = "contato_service_id")
    private List<Telefone> telefones;

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
    }

    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
    }

    public List<Endereco> listarEnderecos() {
        return enderecos;
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public void removerTelefone(Telefone telefone) {
        telefones.remove(telefone);
    }

    public List<Telefone> listarTelefones() {
        return telefones;
    }
}
