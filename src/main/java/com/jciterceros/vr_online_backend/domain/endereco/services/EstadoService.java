package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EstadoDTO;

import java.util.List;
import java.util.Optional;

public interface EstadoService {
    String validateFields(EstadoDTO estadoDTO);

    List<EstadoDTO> listarTodos();

    Optional<EstadoDTO> buscarPorId(Long id);

    EstadoDTO salvar(EstadoDTO estadoDTO);

    EstadoDTO atualizar(Long id, EstadoDTO estadoDTO);

    void deletar(Long id);
}
