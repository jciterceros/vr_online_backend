package com.jciterceros.vr_online_backend.domain.produtos.services;

import com.jciterceros.vr_online_backend.domain.dto.produto.ProdutoDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.produtos.models.Produto;
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
public class ProdutoServiceImpl implements ProdutoService {

    public static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ModelMapper mapper, ProdutoRepository produtoRepository) {
        this.mapper = mapper;
        this.produtoRepository = produtoRepository;
        configureMapper();
    }

    @Override
    public String validateFields(ProdutoDTO productDTO) {
        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(productDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(produto -> mapper.map(produto, ProdutoDTO.class))
                .toList();
    }

    @Override
    public Optional<ProdutoDTO> buscarPorId(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO);
        }
        return produtoRepository.findById(id)
                .map(produto -> mapper.map(produto, ProdutoDTO.class));
    }

    @Override
    public ProdutoDTO salvar(ProdutoDTO productDTO) {
        String error = validateFields(productDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Produto produto = mapper.map(productDTO, Produto.class);
        try {
            produto = produtoRepository.save(produto);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar produto");
        }
        return mapper.map(produto, ProdutoDTO.class);
    }

    @Override
    public ProdutoDTO atualizar(Long id, ProdutoDTO productDTO) {
        String error = validateFields(productDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO);
        }

        Produto produto = mapper.map(productDTO, Produto.class);
        produto.setId(id);

        try {
            produto = produtoRepository.save(produto);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar produto");
        }

        return mapper.map(produto, ProdutoDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO);
        }
        produtoRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<ProdutoDTO, Produto>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
