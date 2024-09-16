package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.dto.produto.LocalArmazenamentoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EnderecoRepository;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.produtos.models.LocalArmazenamento;
import com.jciterceros.vr_online_backend.domain.produtos.repositories.LocalArmazenamentoRepository;
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
public class LocalArmazenamentoServiceImpl implements LocalArmazenamentoService {

    public static final String LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO = "Local de armazenamento não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final LocalArmazenamentoRepository localArmazenamentoRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public LocalArmazenamentoServiceImpl(ModelMapper mapper, LocalArmazenamentoRepository localArmazenamentoRepository, EnderecoRepository enderecoRepository) {
        this.mapper = mapper;
        this.localArmazenamentoRepository = localArmazenamentoRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public String validateFields(LocalArmazenamentoDTO localArmazenamentoDTO) {
        Set<ConstraintViolation<LocalArmazenamentoDTO>> violations = validator.validate(localArmazenamentoDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<LocalArmazenamentoDTO> listarTodos() {
        return localArmazenamentoRepository.findAll().stream()
                .map(localArmazenamento -> mapper.map(localArmazenamento, LocalArmazenamentoDTO.class))
                .toList();
    }

    @Override
    public Optional<LocalArmazenamentoDTO> buscarPorId(Long id) {
        if (!localArmazenamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO);
        }
        return localArmazenamentoRepository.findById(id)
                .map(localArmazenamento -> mapper.map(localArmazenamento, LocalArmazenamentoDTO.class));
    }

    @Override
    public LocalArmazenamentoDTO salvar(LocalArmazenamentoDTO localArmazenamentoDTO) {
        String error = validateFields(localArmazenamentoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Endereco endereco = enderecoRepository.findById(localArmazenamentoDTO.getEndereco().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        LocalArmazenamento localArmazenamento = mapper.map(localArmazenamentoDTO, LocalArmazenamento.class);
        localArmazenamento.setEndereco(endereco);
        try {
            localArmazenamento = localArmazenamentoRepository.save(localArmazenamento);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar local de armazenamento");
        }

        return mapper.map(localArmazenamento, LocalArmazenamentoDTO.class);
    }

    @Override
    public LocalArmazenamentoDTO atualizar(Long id, LocalArmazenamentoDTO localArmazenamentoDTO) {
        String error = validateFields(localArmazenamentoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!localArmazenamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO);
        }

        Endereco endereco = enderecoRepository.findById(localArmazenamentoDTO.getEndereco().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        LocalArmazenamento localArmazenamento = mapper.map(localArmazenamentoDTO, LocalArmazenamento.class);
        localArmazenamento.setId(id);
        localArmazenamento.setEndereco(endereco);
        try {
            localArmazenamento = localArmazenamentoRepository.save(localArmazenamento);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar local de armazenamento");
        }

        return mapper.map(localArmazenamento, LocalArmazenamentoDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!localArmazenamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO);
        }
        localArmazenamentoRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<LocalArmazenamentoDTO, LocalArmazenamento>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
