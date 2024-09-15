package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;

import java.util.List;
import java.util.Optional;

public interface EnderecoService {
    String validateFields(EnderecoDTO enderecoDTO);

    List<EnderecoDTO> listarTodos();

    Optional<EnderecoDTO> buscarPorId(Long id);

    EnderecoDTO salvar(EnderecoDTO enderecoDTO);

    EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO);

    //EnderecoDTO converterParaEndereco(ViaCepDTO viaCepDTO, Integer numero);
    //TODO: Implementar
    EnderecoDTO converterParaEndereco(String cep, Integer numero);

    MunicipioDTO buscarMunicipioPorId(Long id);

    void deletar(Long id);
}
