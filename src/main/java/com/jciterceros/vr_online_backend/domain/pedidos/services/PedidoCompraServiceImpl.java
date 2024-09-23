package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoCompraDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoCompra;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.PedidoCompraRepository;
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
public class PedidoCompraServiceImpl implements PedidoCompraService {

    public static final String PEDIDO_COMPRA_NAO_ENCONTRADO = "Pedido de compra não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final PedidoCompraRepository pedidoCompraRepository;

    @Autowired
    public PedidoCompraServiceImpl(ModelMapper mapper, PedidoCompraRepository pedidoCompraRepository) {
        this.mapper = mapper;
        this.pedidoCompraRepository = pedidoCompraRepository;
        configureMapper();
    }

    @Override
    public String validateFields(PedidoCompraDTO pedidoCompraDTO) {
        Set<ConstraintViolation<PedidoCompraDTO>> violations = validator.validate(pedidoCompraDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<PedidoCompraDTO> listarTodos() {
        return pedidoCompraRepository.findAll().stream()
                .map(pedidoCompra -> mapper.map(pedidoCompra, PedidoCompraDTO.class))
                .toList();
    }

    @Override
    public Optional<PedidoCompraDTO> buscarPorId(Long id) {
        if (!pedidoCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_COMPRA_NAO_ENCONTRADO);
        }
        return pedidoCompraRepository.findById(id)
                .map(pedidoCompra -> mapper.map(pedidoCompra, PedidoCompraDTO.class));
    }

    @Override
    public PedidoCompraDTO salvar(PedidoCompraDTO pedidoCompraDTO) {
        String error = validateFields(pedidoCompraDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        PedidoCompra pedidoCompra = mapper.map(pedidoCompraDTO, PedidoCompra.class);
        try {
            pedidoCompra = pedidoCompraRepository.save(pedidoCompra);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pedido de compra");
        }
        return mapper.map(pedidoCompra, PedidoCompraDTO.class);
    }

    @Override
    public PedidoCompraDTO atualizar(Long id, PedidoCompraDTO pedidoCompraDTO) {
        String error = validateFields(pedidoCompraDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!pedidoCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_COMPRA_NAO_ENCONTRADO);
        }

        PedidoCompra pedidoCompra = mapper.map(pedidoCompraDTO, PedidoCompra.class);
        pedidoCompra.setId(id);

        try {
            pedidoCompra = pedidoCompraRepository.save(pedidoCompra);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar pedido de compra");
        }

        return mapper.map(pedidoCompra, PedidoCompraDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!pedidoCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_COMPRA_NAO_ENCONTRADO);
        }
        pedidoCompraRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<PedidoCompraDTO, PedidoCompra>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}