package com.jciterceros.vr_online_backend.domain.dto.endereco;

public record ViaCepDTO(String cep, String logradouro, String complemento, String bairro, String localidade, String uf,
                        String ibge, String gia, String ddd, String siafi) {
}
