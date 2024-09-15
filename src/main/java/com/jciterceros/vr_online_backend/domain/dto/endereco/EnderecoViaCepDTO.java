package com.jciterceros.vr_online_backend.domain.dto.endereco;

//public record EnderecoViaCepDTO(ViaCepDTO viaCep, Integer numero) {
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoViaCepDTO {
    private ViaCepDTO viaCep;  // Dados do ViaCep
    private Integer numero;
}