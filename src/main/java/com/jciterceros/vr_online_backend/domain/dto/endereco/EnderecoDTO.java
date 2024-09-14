package com.jciterceros.vr_online_backend.domain.dto.endereco;

public record EnderecoDTO(Long id, String rua, Integer numero, String complemento, String cep, String bairro,
                          MunicipioDTO municipio) {
}
