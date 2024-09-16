package com.jciterceros.vr_online_backend.domain.dto.endereco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private Long id;

    @NotNull(message = "Rua é obrigatória")
    @Size(min = 3, max = 100, message = "Rua deve ter entre 3 e 100 caracteres")
    private String rua;

    @NotNull(message = "Número é obrigatório")
    private Integer numero;

    private String complemento;

    @NotNull(message = "CEP é obrigatório")
    @Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres")
    private String cep;

    @NotNull(message = "Bairro é obrigatório")
    @Size(min = 3, max = 100, message = "Bairro deve ter entre 3 e 100 caracteres")
    private String bairro;

    //    @JsonIgnore
    @NotNull(message = "Município é obrigatório")
    private MunicipioDTO municipio;

}