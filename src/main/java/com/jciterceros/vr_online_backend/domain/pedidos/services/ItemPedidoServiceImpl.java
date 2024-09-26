package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.ItemPedidoDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pedidos.models.ItemPedido;
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoCompra;
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoVenda;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.ItemPedidoRepository;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.PedidoCompraRepository;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.PedidoVendaRepository;
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
public class ItemPedidoServiceImpl implements ItemPedidoService {

    public static final String ITEM_PEDIDO_NAO_ENCONTRADO = "Item de pedido não encontrado";
    public static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final ItemPedidoRepository itemPedidoRepository;

    private final ProdutoRepository produtoRepository;
    private final PedidoCompraRepository pedidoCompraRepository;
    private final PedidoVendaRepository pedidoVendaRepository;

    @Autowired
    public ItemPedidoServiceImpl(ModelMapper mapper, ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository, PedidoCompraRepository pedidoCompraRepository, PedidoVendaRepository pedidoVendaRepository) {
        this.mapper = mapper;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoCompraRepository = pedidoCompraRepository;
        this.pedidoVendaRepository = pedidoVendaRepository;
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
        Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));

        ItemPedido itemPedido = mapper.map(itemPedidoDTO, ItemPedido.class);
        itemPedido.setProduto(produto);

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

        Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId())
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));

        ItemPedido itemPedido = mapper.map(itemPedidoDTO, ItemPedido.class);
        itemPedido.setId(id);
        itemPedido.setProduto(produto);

        try {
            itemPedido = itemPedidoRepository.save(itemPedido);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar item de pedido");
        }

        return mapper.map(itemPedido, ItemPedidoDTO.class);
    }

    @Override
    public ItemPedidoDTO adicionarPedidoCompra(Long id, Long pedidoCompraId) {

        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }

        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO));

        PedidoCompra pedidoCompra = pedidoCompraRepository.findById(pedidoCompraId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido de compra não encontrado"));

        itemPedido.setPedidoCompra(pedidoCompra);

        return mapper.map(itemPedidoRepository.save(itemPedido), ItemPedidoDTO.class);
    }

    @Override
    public ItemPedidoDTO adicionarPedidoVenda(Long id, Long pedidoVendaId) {
        if(!itemPedidoRepository.existsById(id)){
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO));

        PedidoVenda pedidoVenda = pedidoVendaRepository.findById(pedidoVendaId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido de venda não encontrado"));

        itemPedido.setPedidoVenda(pedidoVenda);

        return mapper.map(itemPedidoRepository.save(itemPedido), ItemPedidoDTO.class);
    }

    @Override
    public ItemPedidoDTO removerPedidoCompra(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO));

        itemPedido.setPedidoCompra(null);

        return mapper.map(itemPedidoRepository.save(itemPedido), ItemPedidoDTO.class);
    }

    @Override
    public ItemPedidoDTO removerPedidoVenda(Long id) {
        if (!itemPedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO);
        }
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ITEM_PEDIDO_NAO_ENCONTRADO));

        itemPedido.setPedidoVenda(null);

        return mapper.map(itemPedidoRepository.save(itemPedido), ItemPedidoDTO.class);
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
