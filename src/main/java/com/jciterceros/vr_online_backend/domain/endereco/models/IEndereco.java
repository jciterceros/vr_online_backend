package com.jciterceros.vr_online_backend.domain.endereco.models;

public interface IEndereco {
    String getRua();

    Integer getNumero();

    String getComplemento();

    String getCep();

    String getBairro();

    Municipio getMunicipio();
}
