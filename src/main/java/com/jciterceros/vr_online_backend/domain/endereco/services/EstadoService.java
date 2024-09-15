package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EstadoDTO;

import java.util.List;
import java.util.Optional;
public interface EstadoService {
    String validateFields(EstadoDTO estadoDTO);
    EstadoDTO salvar(EstadoDTO estadoDTO);
    Optional<EstadoDTO> buscarPorId(Long id);
    List<EstadoDTO> listarTodos();
    void deletar(Long id);
    EstadoDTO atualizar(Long id, EstadoDTO estadoDTO);
}
