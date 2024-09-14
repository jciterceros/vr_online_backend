package com.jciterceros.vr_online_backend.model.adapters;

import com.jciterceros.vr_online_backend.model.IEndereco;
import com.jciterceros.vr_online_backend.model.Municipio;
import com.jciterceros.vr_online_backend.model.ViaCep;
import lombok.Data;

@Data
public class EnderecoViaCepAdapter implements IEndereco {
    private ViaCep viaCep;
    private Integer numero;

    @Override
    public String getRua() {
        return viaCep.getLogradouro();
    }

    @Override
    public Integer getNumero() {
        return numero;
    }

    @Override
    public String getComplemento() {
        return viaCep.getComplemento();
    }

    @Override
    public String getCep() {
        return viaCep.getCep();
    }

    @Override
    public String getBairro() {
        return viaCep.getBairro();
    }

    @Override
    public Municipio getMunicipio() {
        Municipio municipio = new Municipio();
        municipio.setDescricao(viaCep.getLocalidade());
        return municipio;
    }
}
