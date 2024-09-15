package com.jciterceros.vr_online_backend.domain.dto.endereco;

//public record MunicipioDTO(Long id, String descricao, EstadoDTO estado) {
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioDTO {
    private Long id;
    private String descricao;
    private EstadoDTO estado;  // Associação com EstadoDTO
}