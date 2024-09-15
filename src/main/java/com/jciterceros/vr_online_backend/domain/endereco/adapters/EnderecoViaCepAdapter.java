package com.jciterceros.vr_online_backend.domain.endereco.adapters;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.endereco.models.IEndereco;
import com.jciterceros.vr_online_backend.domain.endereco.models.Municipio;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.MunicipioRepository;
import com.jciterceros.vr_online_backend.domain.endereco.services.ViaCepService;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoViaCepAdapter implements IEndereco {
    private ViaCepDTO viaCepDTO;
    private Integer numero;
    private final ModelMapper mapper;
    private final MunicipioRepository municipioRepository;
    private final ViaCepService viaCepService;

    @Autowired
    public EnderecoViaCepAdapter(ModelMapper mapper, MunicipioRepository municipioRepository, ViaCepService viaCepService) {
        this.mapper = mapper;
        this.municipioRepository = municipioRepository;
        this.viaCepService = viaCepService;
        configureMapper();
    }

    public EnderecoDTO converterParaEndereco(String cep, Integer numero) {
        viaCepDTO = viaCepService.buscarEnderecoPorCep(cep);
        if (viaCepDTO == null || viaCepDTO.getIbge() == null) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }

        Municipio municipio = municipioRepository.findById(Long.parseLong(viaCepDTO.getIbge()))
                .orElseThrow(() -> new ResourceNotFoundException("Municipio não encontrado"));

        EnderecoDTO enderecoDTO = new EnderecoDTO();

        enderecoDTO.setRua(viaCepDTO.getLogradouro());
        enderecoDTO.setNumero(numero);
        enderecoDTO.setComplemento(viaCepDTO.getComplemento());
        enderecoDTO.setCep(viaCepDTO.getCep().replace("-", ""));
        enderecoDTO.setBairro(viaCepDTO.getBairro());

        enderecoDTO.setMunicipio(mapper.map(municipio, MunicipioDTO.class));
        return enderecoDTO;
    }

    @Override
    public String getRua() {
        return viaCepDTO.getLogradouro();
    }

    @Override
    public Integer getNumero() {
        return numero;
    }

    @Override
    public String getComplemento() {
        return viaCepDTO.getComplemento();
    }

    @Override
    public String getCep() {
        return viaCepDTO.getCep();
    }

    @Override
    public String getBairro() {
        return viaCepDTO.getBairro();
    }

    @Override
    public Municipio getMunicipio() {
        Municipio municipio = new Municipio();
        municipio.setDescricao(viaCepDTO.getLocalidade());
        return municipio;
    }

    public void configureMapper() {
        mapper.typeMap(EnderecoViaCepAdapter.class, Endereco.class)
                .addMappings(mapping -> mapping.skip(Endereco::setId))
                .addMappings(mapping -> mapping.skip(Endereco::setMunicipio))
                .addMappings(mapping -> mapping.skip(Endereco::setNumero));
    }
}
