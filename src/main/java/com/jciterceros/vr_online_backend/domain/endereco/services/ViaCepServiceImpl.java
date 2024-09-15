package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepServiceImpl implements ViaCepService {
    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    @Override
    public ViaCepDTO buscarEnderecoPorCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(VIACEP_URL, ViaCepDTO.class, cep);
    }
}
