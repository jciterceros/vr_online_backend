package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;

public interface ViaCepService {
    ViaCepDTO buscarEnderecoPorCep(String cep);
}
