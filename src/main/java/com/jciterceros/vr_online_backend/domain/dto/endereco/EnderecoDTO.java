package com.jciterceros.vr_online_backend.domain.dto.endereco;

//public record EnderecoDTO(Long id, String rua, Integer numero, String complemento, String cep, String bairro,
//                          MunicipioDTO municipio) {
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private Long id;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cep;
    private String bairro;
    private MunicipioDTO municipio;  // Associação com MunicipioDTO
}