package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Estado;
import com.jciterceros.vr_online_backend.domain.endereco.models.Municipio;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EstadoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.MunicipioRepository;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipioServiceImpl implements MunicipioService {

    private final ModelMapper mapper;
    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public MunicipioServiceImpl(ModelMapper mapper, MunicipioRepository municipioRepository, EstadoRepository estadoRepository) {
        this.mapper = mapper;
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
        configureMapper();
    }

    @Override
    public MunicipioDTO salvar(MunicipioDTO municipioDTO) {
        Estado estado = estadoRepository.findById(municipioDTO.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado"));

        Municipio municipio = mapper.map(municipioDTO, Municipio.class);
        municipio.setEstado(estado);
        municipio = municipioRepository.save(municipio);

        return mapper.map(municipio, MunicipioDTO.class);
    }

    @Override
    public Optional<MunicipioDTO> buscarPorId(Long id) {
        return municipioRepository.findById(id)
                .map(municipio -> mapper.map(municipio, MunicipioDTO.class));
    }

    @Override
    public List<MunicipioDTO> listarTodos() {
        return municipioRepository.findAll().stream()
                .map(municipio -> mapper.map(municipio, MunicipioDTO.class))
                .toList();
    }

    @Override
    public void deletar(Long id) {
        municipioRepository.deleteById(id);
    }

    @Override
    public MunicipioDTO atualizar(Long id, MunicipioDTO municipioDTO) {
        if (!municipioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Municipio não encontrado");
        }

        Estado estado = estadoRepository.findById(municipioDTO.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado"));

        Municipio municipio = mapper.map(municipioDTO, Municipio.class);
        municipio.setEstado(estado);
        municipio = municipioRepository.save(municipio);

        return mapper.map(municipio, MunicipioDTO.class);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<MunicipioDTO, Municipio>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getEstado());
            }
        });
    }
}
