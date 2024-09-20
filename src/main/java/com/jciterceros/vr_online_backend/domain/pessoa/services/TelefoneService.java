package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.TelefoneDTO;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Telefone;

import java.util.List;
import java.util.Optional;

public interface TelefoneService {
    String validateFields(TelefoneDTO telefoneDTO);

    List<TelefoneDTO> listarTodos();

    Optional<TelefoneDTO> buscarPorId(Long id);

    TelefoneDTO salvar(TelefoneDTO telefoneDTO);

    List<Telefone> salvarLista(Long id, List<TelefoneDTO> telefoneDTOs);

    TelefoneDTO atualizar(Long id, TelefoneDTO telefoneDTO);

    void deletar(Long id);
}
