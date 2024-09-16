package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.dto.produto.EstoqueDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.produtos.models.Estoque;
import com.jciterceros.vr_online_backend.domain.produtos.models.LocalArmazenamento;
import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;
import com.jciterceros.vr_online_backend.domain.produtos.repositories.EstoqueRepository;
import com.jciterceros.vr_online_backend.domain.produtos.repositories.LocalArmazenamentoRepository;
import com.jciterceros.vr_online_backend.domain.produtos.repositories.ProdutoRepository;
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
public class EstoqueServiceImpl implements EstoqueService {

    public static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
    public static final String ESTOQUE_NAO_ENCONTRADO = "Estoque não encontrado";
    public static final String LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO = "Local de armazenamento não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final EstoqueRepository estoqueRepository;

    private final ProdutoRepository produtoRepository;

    private final LocalArmazenamentoRepository localArmazenamentoRepository;

    @Autowired
    public EstoqueServiceImpl(ModelMapper mapper, EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository, LocalArmazenamentoRepository localArmazenamentoRepository) {
        this.mapper = mapper;
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.localArmazenamentoRepository = localArmazenamentoRepository;
        configureMapper();
    }

    @Override
    public String validateFields(EstoqueDTO estoqueDTO) {
        Set<ConstraintViolation<EstoqueDTO>> violations = validator.validate(estoqueDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<EstoqueDTO> listarTodos() {
        return estoqueRepository.findAll().stream()
                .map(estoque -> mapper.map(estoque, EstoqueDTO.class))
                .toList();
    }

    @Override
    public Optional<EstoqueDTO> buscarPorId(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTOQUE_NAO_ENCONTRADO);
        }
        return estoqueRepository.findById(id)
                .map(estoque -> mapper.map(estoque, EstoqueDTO.class));
    }

    @Override
    public EstoqueDTO salvar(EstoqueDTO estoqueDTO) {
        String error = validateFields(estoqueDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Produto produto = produtoRepository.findById(estoqueDTO.getProduto().getId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));

        LocalArmazenamento localArmazenamento = localArmazenamentoRepository.findById(estoqueDTO.getLocalArmazenamento().getId())
                .orElseThrow(() -> new ResourceNotFoundException(LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO));

        Estoque estoque = mapper.map(estoqueDTO, Estoque.class);
        estoque.setProduto(produto);
        estoque.setLocalArmazenamento(localArmazenamento);
        try {
            estoque = estoqueRepository.save(estoque);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar estoque");
        }

        return mapper.map(estoque, EstoqueDTO.class);
    }

    @Override
    public EstoqueDTO atualizar(Long id, EstoqueDTO estoqueDTO) {
        String error = validateFields(estoqueDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!estoqueRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTOQUE_NAO_ENCONTRADO);
        }

        Produto produto = produtoRepository.findById(estoqueDTO.getProduto().getId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));

        LocalArmazenamento localArmazenamento = localArmazenamentoRepository.findById(estoqueDTO.getLocalArmazenamento().getId())
                .orElseThrow(() -> new ResourceNotFoundException(LOCAL_DE_ARMAZENAMENTO_NAO_ENCONTRADO));

        Estoque estoque = mapper.map(estoqueDTO, Estoque.class);
        estoque.setId(id);
        estoque.setProduto(produto);
        estoque.setLocalArmazenamento(localArmazenamento);
        try {
            estoque = estoqueRepository.save(estoque);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar estoque");
        }

        return mapper.map(estoque, EstoqueDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new ResourceNotFoundException(ESTOQUE_NAO_ENCONTRADO);
        }
        estoqueRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<EstoqueDTO, Estoque>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
