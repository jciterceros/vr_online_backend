package com.jciterceros.vr_online_backend.model;

public interface IEndereco {
    String getRua();
    Integer getNumero();
    String getComplemento();
    String getCep();
    String getBairro();
    Municipio getMunicipio();
}
