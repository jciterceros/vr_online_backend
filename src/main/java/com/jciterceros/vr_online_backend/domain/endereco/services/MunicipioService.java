package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;

import java.util.List;
import java.util.Optional;

public interface MunicipioService {
    MunicipioDTO salvar(MunicipioDTO municipioDTO);

    Optional<MunicipioDTO> buscarPorId(Long id);

    List<MunicipioDTO> listarTodos();

    void deletar(Long id);

    MunicipioDTO atualizar(Long id, MunicipioDTO municipioDTO);
}
