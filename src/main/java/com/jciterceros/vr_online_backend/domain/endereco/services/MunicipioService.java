package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;

import java.util.List;
import java.util.Optional;

public interface MunicipioService {
    String validateFields(MunicipioDTO municipioDTO);

    List<MunicipioDTO> listarTodos();

    Optional<MunicipioDTO> buscarPorId(Long id);

    MunicipioDTO salvar(MunicipioDTO municipioDTO);

    MunicipioDTO atualizar(Long id, MunicipioDTO municipioDTO);

    void deletar(Long id);
}
