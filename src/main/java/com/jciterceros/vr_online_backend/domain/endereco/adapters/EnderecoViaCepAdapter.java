package com.jciterceros.vr_online_backend.domain.endereco.adapters;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.EstadoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.*;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EstadoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.MunicipioRepository;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Data
@Component
public class EnderecoViaCepAdapter implements IEndereco {
    private ViaCep viaCep;
    private Integer numero;

    private final ModelMapper mapper;

    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public EnderecoViaCepAdapter(ModelMapper mapper, MunicipioRepository municipioRepository, EstadoRepository estadoRepository) {
        this.mapper = mapper;
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
        configureMapper();
    }

    public EnderecoDTO converterParaEndereco(ViaCepDTO viaCepDTO) {
        Municipio municipio = municipioRepository.findById(Long.parseLong(viaCepDTO.getIbge()))
                .orElseThrow(() -> new ResourceNotFoundException("Municipio não encontrado"));

        Estado estado = estadoRepository.findById(municipio.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado"));

        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua(viaCepDTO.getLogradouro());
        enderecoDTO.setComplemento(viaCepDTO.getComplemento());
        enderecoDTO.setCep(viaCepDTO.getCep());
        enderecoDTO.setBairro(viaCepDTO.getBairro());

        enderecoDTO.setMunicipio(mapper.map(municipio, MunicipioDTO.class));
//        enderecoDTO.setMunicipio(new MunicipioDTO(municipio.getId(), municipio.getDescricao(),new EstadoDTO(estado.getId(), estado.getDescricao(), estado.getSigla())));
        return enderecoDTO;
    }

    @Override
    public String getRua() {
        return viaCep.getLogradouro();
    }

    @Override
    public Integer getNumero() {
        return numero;
    }

    @Override
    public String getComplemento() {
        return viaCep.getComplemento();
    }

    @Override
    public String getCep() {
        return viaCep.getCep();
    }

    @Override
    public String getBairro() {
        return viaCep.getBairro();
    }

    @Override
    public Municipio getMunicipio() {
        Municipio municipio = new Municipio();
        municipio.setDescricao(viaCep.getLocalidade());
        return municipio;
    }

    public void configureMapper() {
        mapper.typeMap(EnderecoViaCepAdapter.class, Endereco.class)
                .addMappings(mapping -> mapping.skip(Endereco::setId))
                .addMappings(mapping -> mapping.skip(Endereco::setMunicipio))
                .addMappings(mapping -> mapping.skip(Endereco::setNumero));
    }
}
