package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EstadoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Estado;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EstadoRepository;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
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
public class EstadoServiceImpl implements EstadoService {

    public static final String ESTADO_NAO_ENCONTRADO = "Estado não encontrado";
    private final ModelMapper mapper;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoServiceImpl(ModelMapper mapper, EstadoRepository estadoRepository) {
        this.mapper = mapper;
        this.estadoRepository = estadoRepository;
        configureMapper();
    }

    @Override
    public String validateFields(EstadoDTO estadoDTO) {
        Set<ConstraintViolation<EstadoDTO>> violations = validator.validate(estadoDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public EstadoDTO salvar(EstadoDTO estadoDTO) {
        String error = validateFields(estadoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Estado estado = mapper.map(estadoDTO, Estado.class);
        if (Boolean.TRUE.equals(estadoRepository.existsBySigla(estado.getSigla()))) {
            throw new DatabaseException("Já existe um estado com a sigla informada");
        }

        try {
            estado = estadoRepository.save(estado);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar estado");
        }

        return mapper.map(estado, EstadoDTO.class);
    }

    @Override
    public Optional<EstadoDTO> buscarPorId(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTADO_NAO_ENCONTRADO);
        }
        return estadoRepository.findById(id)
                .map(estado -> mapper.map(estado, EstadoDTO.class));
    }

    @Override
    public List<EstadoDTO> listarTodos() {
        return estadoRepository.findAll().stream()
                .map(estado -> mapper.map(estado, EstadoDTO.class))
                .toList();
    }

    @Override
    public void deletar(Long id) {
        if (!estadoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTADO_NAO_ENCONTRADO);
        }
        estadoRepository.deleteById(id);
    }

    @Override
    public EstadoDTO atualizar(Long id, EstadoDTO estadoDTO) {
        String error = validateFields(estadoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!estadoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTADO_NAO_ENCONTRADO);
        }
        Estado estado = mapper.map(estadoDTO, Estado.class);
        estado = estadoRepository.save(estado);

        return mapper.map(estado, EstadoDTO.class);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<EstadoDTO, Estado>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
