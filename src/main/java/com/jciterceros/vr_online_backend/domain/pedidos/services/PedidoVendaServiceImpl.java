package com.jciterceros.vr_online_backend.domain.pedidos.services;


import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoVendaDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.repositories.PagamentoRepository;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.PagamentoBitcoins;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.PagamentoBoleto;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.PagamentoCartao;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.PagamentoPIX;
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoVenda;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.PedidoVendaRepository;
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
public class PedidoVendaServiceImpl implements PedidoVendaService {

    public static final String PEDIDO_VENDA_NAO_ENCONTRADO = "Pedido de venda não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final PedidoVendaRepository pedidoVendaRepository;

    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PedidoVendaServiceImpl(ModelMapper mapper, PedidoVendaRepository pedidoVendaRepository, PagamentoRepository pagamentoRepository) {
        this.mapper = mapper;
        this.pedidoVendaRepository = pedidoVendaRepository;
        this.pagamentoRepository = pagamentoRepository;
        configureMapper();
    }

    @Override
    public String validateFields(PedidoVendaDTO pedidoVendaDTO) {
        Set<ConstraintViolation<PedidoVendaDTO>> violations = validator.validate(pedidoVendaDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<PedidoVendaDTO> listarTodos() {
        return pedidoVendaRepository.findAll().stream()
                .map(pedidoVenda -> mapper.map(pedidoVenda, PedidoVendaDTO.class))
                .toList();
    }

    @Override
    public Optional<PedidoVendaDTO> buscarPorId(Long id) {
        if (!pedidoVendaRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_VENDA_NAO_ENCONTRADO);
        }
        return pedidoVendaRepository.findById(id)
                .map(pedidoVenda -> mapper.map(pedidoVenda, PedidoVendaDTO.class));
    }

    @Override
    public PedidoVendaDTO salvar(PedidoVendaDTO pedidoVendaDTO) {
        String error = validateFields(pedidoVendaDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        // Buscando o pagamento pelo ID
        Pagamento pagamento = pagamentoRepository.findById(pedidoVendaDTO.getPagamentoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));

        // Criando a estratégia de pagamento
        switch (pagamento.getTipoPagamento()) {
            case PIX:
                pagamento.setProcessar(new PagamentoPIX());
                break;
            case BOLETO:
                pagamento.setProcessar(new PagamentoBoleto());
                break;
            case CARTAO:
                pagamento.setProcessar(new PagamentoCartao());
                break;
            case BITCOINS:
                pagamento.setProcessar(new PagamentoBitcoins());
                break;
            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido");
        }

        // Processando o pagamento
        pagamento.processar();

        PedidoVenda pedidoVenda = mapper.map(pedidoVendaDTO, PedidoVenda.class);
        pedidoVenda.setPagamento(pagamento); // Adicionando o pagamento ao pedido de venda

        try {
            pedidoVenda = pedidoVendaRepository.save(pedidoVenda);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pedido de venda");
        }

        return mapper.map(pedidoVenda, PedidoVendaDTO.class);
    }

    @Override
    public PedidoVendaDTO atualizar(Long id, PedidoVendaDTO pedidoVendaDTO) {
        String error = validateFields(pedidoVendaDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!pedidoVendaRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_VENDA_NAO_ENCONTRADO);
        }

        PedidoVenda pedidoVenda = mapper.map(pedidoVendaDTO, PedidoVenda.class);
        pedidoVenda.setId(id);

        try {
            pedidoVenda = pedidoVendaRepository.save(pedidoVenda);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar pedido de venda");
        }

        return mapper.map(pedidoVenda, PedidoVendaDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!pedidoVendaRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_VENDA_NAO_ENCONTRADO);
        }
        pedidoVendaRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<PedidoVendaDTO, PedidoVenda>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }
}