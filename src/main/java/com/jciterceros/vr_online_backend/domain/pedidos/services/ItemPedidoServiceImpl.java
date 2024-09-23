package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.ItemPedidoDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pedidos.models.ItemPedido;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.ItemPedidoRepository;
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
public class ItemPedidoServiceImpl implements ItemPedidoService {

    public static final String ITEM_PEDIDO_NAO_ENCONTRADO = "Item de pedido não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public ItemPedidoServiceImpl(ModelMapper mapper, ItemPedidoRepository itemPedidoRepository) {
        this.mapper = mapper;
        this.itemPedidoRepository = itemPedidoRepository;
        configureMapper();
    }

    @Override
    public String validateFields(ItemPedidoDTO itemPedidoDTO) {
        Set<ConstraintViolation<ItemPedidoDTO>> violations = validator.validate(itemPedidoDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<ItemPedidoDTO> listarTodos() {
        return itemPedidoRepository.findAll().stream()
                .map(itemPedido -> mapper.map(itemPedido, ItemPedidoDTO.class))
                .toList();
    }

    @Override
    public Optional<ItemPedidoDTO> buscarPorId(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }
        return itemPedidoRepository.findById(id)
                .map(itemPedido -> mapper.map(itemPedido, ItemPedidoDTO.class));
    }

    @Override
    public ItemPedidoDTO salvar(ItemPedidoDTO itemPedidoDTO) {
        String error = validateFields(itemPedidoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        ItemPedido itemPedido = mapper.map(itemPedidoDTO, ItemPedido.class);
        try {
            itemPedido = itemPedidoRepository.save(itemPedido);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar item de pedido");
        }
        return mapper.map(itemPedido, ItemPedidoDTO.class);
    }

    @Override
    public ItemPedidoDTO atualizar(Long id, ItemPedidoDTO itemPedidoDTO) {
        String error = validateFields(itemPedidoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }

        ItemPedido itemPedido = mapper.map(itemPedidoDTO, ItemPedido.class);
        itemPedido.setId(id);

        try {
            itemPedido = itemPedidoRepository.save(itemPedido);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar item de pedido");
        }

        return mapper.map(itemPedido, ItemPedidoDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }
        itemPedidoRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<ItemPedidoDTO, ItemPedido>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}
