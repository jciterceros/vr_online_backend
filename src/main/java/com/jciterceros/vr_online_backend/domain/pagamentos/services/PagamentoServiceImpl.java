package com.jciterceros.vr_online_backend.domain.pagamentos.services;

import com.jciterceros.vr_online_backend.domain.dto.pagamento.PagamentoDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.repositories.PagamentoRepository;
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
public class PagamentoServiceImpl implements PagamentoService {

    public static final String PAGAMENTO_NAO_ENCONTRADO = "Pagamento não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoServiceImpl(ModelMapper mapper, PagamentoRepository pagamentoRepository) {
        this.mapper = mapper;
        this.pagamentoRepository = pagamentoRepository;
        configureMapper();
    }

    @Override
    public String validateFields(PagamentoDTO pagamentoDTO) {
        Set<ConstraintViolation<PagamentoDTO>> violations = validator.validate(pagamentoDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<PagamentoDTO> listarTodos() {
        return pagamentoRepository.findAll().stream()
                .map(pagamento -> mapper.map(pagamento, PagamentoDTO.class))
                .toList();
    }

    @Override
    public Optional<PagamentoDTO> buscarPorId(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PAGAMENTO_NAO_ENCONTRADO);
        }
        return pagamentoRepository.findById(id)
                .map(pagamento -> mapper.map(pagamento, PagamentoDTO.class));
    }

    @Override
    public PagamentoDTO salvar(PagamentoDTO pagamentoDTO) {
        String error = validateFields(pagamentoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Pagamento pagamento = mapper.map(pagamentoDTO, Pagamento.class);
        try {
            pagamento = pagamentoRepository.save(pagamento);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pagamento");
        }
        return mapper.map(pagamento, PagamentoDTO.class);
    }

    @Override
    public PagamentoDTO atualizar(Long id, PagamentoDTO pagamentoDTO) {
        String error = validateFields(pagamentoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PAGAMENTO_NAO_ENCONTRADO);
        }

        Pagamento pagamento = mapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setId(id);

        try {
            pagamento = pagamentoRepository.save(pagamento);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar pagamento");
        }

        return mapper.map(pagamento, PagamentoDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PAGAMENTO_NAO_ENCONTRADO);
        }
        pagamentoRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<PagamentoDTO, Pagamento>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}