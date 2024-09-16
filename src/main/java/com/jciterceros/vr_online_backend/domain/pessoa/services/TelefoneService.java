package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.TelefoneDTO;

import java.util.List;
import java.util.Optional;

public interface TelefoneService {
    String validateFields(TelefoneDTO telefoneDTO);

    List<TelefoneDTO> listarTodos();

    Optional<TelefoneDTO> buscarPorId(Long id);

    TelefoneDTO salvar(TelefoneDTO telefoneDTO);

    TelefoneDTO atualizar(Long id, TelefoneDTO telefoneDTO);

    void deletar(Long id);
}
