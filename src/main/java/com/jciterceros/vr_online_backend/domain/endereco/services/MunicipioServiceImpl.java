package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Estado;
import com.jciterceros.vr_online_backend.domain.endereco.models.Municipio;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EstadoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.MunicipioRepository;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.exception.handler.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MunicipioServiceImpl implements MunicipioService {

    public static final String MUNICIPIO_NAO_ENCONTRADO = "Municipio não encontrado";
    public static final String ESTADO_NAO_ENCONTRADO = "Estado não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
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
    public String validateFields(MunicipioDTO municipioDTO) {
        Set<ConstraintViolation<MunicipioDTO>> violations = validator.validate(municipioDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<MunicipioDTO> listarTodos() {
        return municipioRepository.findAll().stream()
                .map(municipio -> mapper.map(municipio, MunicipioDTO.class))
                .toList();
    }

    @Override
    public Optional<MunicipioDTO> buscarPorId(Long id) {
        if (!municipioRepository.existsById(id)) {
            throw new ResourceNotFoundException(MUNICIPIO_NAO_ENCONTRADO);
        }
        return municipioRepository.findById(id)
                .map(municipio -> mapper.map(municipio, MunicipioDTO.class));
    }

    @Override
    public MunicipioDTO salvar(MunicipioDTO municipioDTO) {
        String error = validateFields(municipioDTO);
        if(error != null) {
            throw new DatabaseException(error);
        }

        Estado estado = estadoRepository.findById(municipioDTO.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NAO_ENCONTRADO));

        Municipio municipio = mapper.map(municipioDTO, Municipio.class);
        municipio.setEstado(estado);

        try {
            municipio = municipioRepository.save(municipio);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar municipio");
        }

        return mapper.map(municipio, MunicipioDTO.class);
    }

    @Override
    public MunicipioDTO atualizar(Long id, MunicipioDTO municipioDTO) {
        String error = validateFields(municipioDTO);
        if(error != null) {
            throw new DatabaseException(error);
        }

        if (id == null) {
            throw new MethodArgumentNotValidException("Id não pode ser nulo");
        }

        if (!municipioRepository.existsById(id)) {
            throw new ResourceNotFoundException(MUNICIPIO_NAO_ENCONTRADO);
        }

        Estado estado = estadoRepository.findById(municipioDTO.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(ESTADO_NAO_ENCONTRADO));

        Municipio municipio = mapper.map(municipioDTO, Municipio.class);
        municipio.setId(id);
        municipio.setEstado(estado);

        try {
            municipio = municipioRepository.save(municipio);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar municipio");
        }

        return mapper.map(municipio, MunicipioDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!municipioRepository.existsById(id)) {
            throw new ResourceNotFoundException(MUNICIPIO_NAO_ENCONTRADO);
        }
        municipioRepository.deleteById(id);
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
