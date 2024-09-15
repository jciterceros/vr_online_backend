package com.jciterceros.vr_online_backend.domain.dto.endereco;

//public record ViaCepDTO(String cep, String logradouro, String complemento, String bairro, String localidade, String uf,
//                        String ibge, String gia, String ddd, String siafi) {
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViaCepDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
}