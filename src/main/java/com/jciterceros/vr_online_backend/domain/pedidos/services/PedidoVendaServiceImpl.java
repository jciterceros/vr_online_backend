package com.jciterceros.vr_online_backend.domain.pedidos.services;


import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoVendaDTO;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pagamentos.factories.PagamentoProcessarFactory;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoProcessar;
import com.jciterceros.vr_online_backend.domain.pagamentos.repositories.PagamentoRepository;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations.PagamentoProcessamentoService;
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

    private final PagamentoProcessamentoService pagamentoProcessamentoService;

    @Autowired
    private PagamentoProcessarFactory pagamentoProcessarFactory;

    @Autowired
    public PedidoVendaServiceImpl(ModelMapper mapper, PedidoVendaRepository pedidoVendaRepository, PagamentoRepository pagamentoRepository, PagamentoProcessamentoService pagamentoProcessamentoService) {
        this.mapper = mapper;
        this.pedidoVendaRepository = pedidoVendaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoProcessamentoService = pagamentoProcessamentoService;
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

        // Criar um Novo Pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento(pedidoVendaDTO.getTipoPagamento());
        pagamento.setValor(pedidoVendaDTO.getValorTotal()); // Supondo que o valor total do pedido seja o valor do pagamento

        // Obtém a estratégia de processamento de pagamento correta e processa o pagamento
        IPagamentoProcessar processar = pagamentoProcessarFactory.getPagamentoProcessar(pagamento.getTipoPagamento());
        processar.processarPagamento(pagamento);

        // Processando o pagamento
        pagamentoProcessamentoService.processarPagamento(pagamento);

        // Persiste o pagamento no banco de dados
        pagamento = pagamentoRepository.save(pagamento);

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