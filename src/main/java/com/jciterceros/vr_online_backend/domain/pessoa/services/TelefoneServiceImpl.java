package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.TelefoneDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Telefone;
import com.jciterceros.vr_online_backend.domain.pessoa.repositories.TelefoneRepository;
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
public class TelefoneServiceImpl implements TelefoneService {

    public static final String TELEFONE_NAO_ENCONTRADO = "Telefone não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final TelefoneRepository telefoneRepository;

    @Autowired
    public TelefoneServiceImpl(ModelMapper mapper, TelefoneRepository telefoneRepository) {
        this.mapper = mapper;
        this.telefoneRepository = telefoneRepository;
    }

    @Override
    public String validateFields(TelefoneDTO telefoneDTO) {
        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefoneDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<TelefoneDTO> listarTodos() {
        return telefoneRepository.findAll().stream()
                .map(telefone -> mapper.map(telefone, TelefoneDTO.class))
                .toList();
    }

    @Override
    public Optional<TelefoneDTO> buscarPorId(Long id) {
        if (!telefoneRepository.existsById(id)) {
            throw new ResourceNotFoundException(TELEFONE_NAO_ENCONTRADO);
        }
        return telefoneRepository.findById(id)
                .map(telefone -> mapper.map(telefone, TelefoneDTO.class));
    }

    @Override
    public TelefoneDTO salvar(TelefoneDTO telefoneDTO) {
        String error = validateFields(telefoneDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Telefone telefone = mapper.map(telefoneDTO, Telefone.class);
        try {
            telefone = telefoneRepository.save(telefone);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar telefone");
        }

        return mapper.map(telefone, TelefoneDTO.class);
    }

    @Override
    public TelefoneDTO atualizar(Long id, TelefoneDTO telefoneDTO) {
        String error = validateFields(telefoneDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }
        if (!telefoneRepository.existsById(id)) {
            throw new ResourceNotFoundException(TELEFONE_NAO_ENCONTRADO);
        }

        Telefone telefone = mapper.map(telefoneDTO, Telefone.class);
        telefone.setId(id);
        try {
            telefone = telefoneRepository.save(telefone);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar telefone");
        }

        return mapper.map(telefone, TelefoneDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!telefoneRepository.existsById(id)) {
            throw new DatabaseException(TELEFONE_NAO_ENCONTRADO);
        }
        telefoneRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<TelefoneDTO, Telefone>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
