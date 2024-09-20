package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.ContatoDTO;

import java.util.List;
import java.util.Optional;

public interface ContatoService {

    String validateFields(ContatoDTO contatoDTO);

    List<ContatoDTO> listarTodos();

    Optional<ContatoDTO> buscarPorId(Long id);

    ContatoDTO salvar(ContatoDTO contatoDTO);

    ContatoDTO atualizar(Long id, ContatoDTO contatoDTO);

    void deletar(Long id);
}
