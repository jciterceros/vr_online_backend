package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.dto.produto.LocalArmazenamentoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LocalArmazenamentoService {

    String validateFields(LocalArmazenamentoDTO localArmazenamentoDTO);

    List<LocalArmazenamentoDTO> listarTodos();

    Optional<LocalArmazenamentoDTO> buscarPorId(Long id);

    LocalArmazenamentoDTO salvar(LocalArmazenamentoDTO localArmazenamentoDTO);

    LocalArmazenamentoDTO atualizar(Long id, LocalArmazenamentoDTO localArmazenamentoDTO);

    void deletar(Long id);
}
