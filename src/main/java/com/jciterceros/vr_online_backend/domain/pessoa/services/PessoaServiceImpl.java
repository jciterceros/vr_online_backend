package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.pessoa.PessoaDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import com.jciterceros.vr_online_backend.domain.pessoa.repositories.PessoaRepository;
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
public class PessoaServiceImpl implements PessoaService {

    public static final String PESSOA_NAO_ENCONTRADA = "Pessoa não encontrada";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final PessoaFactory pessoaFactory;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImpl(ModelMapper mapper, PessoaFactory pessoaFactory, PessoaRepository pessoaRepository) {
        this.mapper = mapper;
        this.pessoaFactory = pessoaFactory;
        this.pessoaRepository = pessoaRepository;
        configureMapper();
    }

    @Override
    public String validateFields(PessoaDTO pessoaDTO) {
        Set<ConstraintViolation<PessoaDTO>> violations = validator.validate(pessoaDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<PessoaDTO> listarTodos() {
        return pessoaRepository.findAll().stream()
                .map(pessoa -> mapper.map(pessoa, PessoaDTO.class))
                .toList();
    }

    @Override
    public Optional<PessoaDTO> buscarPorId(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException(PESSOA_NAO_ENCONTRADA);
        }
        return pessoaRepository.findById(id)
                .map(pessoa -> mapper.map(pessoa, PessoaDTO.class));
    }

    @Override
    public PessoaDTO salvar(PessoaDTO pessoaDTO) {
        String error = validateFields(pessoaDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Pessoa pessoa = pessoaFactory.createPessoa(pessoaDTO);

        try {
            pessoaRepository.save(pessoa);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar pessoa");
        }

        return mapper.map(pessoa, PessoaDTO.class);
    }

    @Override
    public PessoaDTO atualizar(Long id, PessoaDTO pessoaDTO) {
        String error = validateFields(pessoaDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException(PESSOA_NAO_ENCONTRADA);
        }

        Pessoa pessoa = pessoaFactory.createPessoa(pessoaDTO);
        pessoa.setId(id);
        try {
            pessoaRepository.save(pessoa);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar pessoa");
        }

        return mapper.map(pessoa, PessoaDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new DatabaseException(PESSOA_NAO_ENCONTRADA);
        }
        pessoaRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<PessoaDTO, Pessoa>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
