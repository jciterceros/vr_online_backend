package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.PessoaDTO;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    String validateFields(PessoaDTO pessoaDTO);

    List<PessoaDTO> listarTodos();

    Optional<PessoaDTO> buscarPorId(Long id);

    PessoaDTO salvar(PessoaDTO pessoaDTO);

    PessoaDTO atualizar(Long id, PessoaDTO pessoaDTO);

    void deletar(Long id);
}
