package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {
    EnderecoDTO salvar(EnderecoDTO enderecoDTO);

    Optional<EnderecoDTO> buscarPorId(Long id);

    List<EnderecoDTO> listarTodos();

    void deletar(Long id);

    EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO);

    EnderecoDTO converterParaEndereco(ViaCepDTO viaCepDTO, Integer numero);

    MunicipioDTO buscarMunicipioPorId(Long id);
}
