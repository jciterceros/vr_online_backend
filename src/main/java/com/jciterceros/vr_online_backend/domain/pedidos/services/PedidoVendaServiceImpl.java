package com.jciterceros.vr_online_backend.domain.pedidos.services;


import com.jciterceros.vr_online_backend.domain.dto.pedido.ItemPedidoDTO;
import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoVendaDTO;
import com.jciterceros.vr_online_backend.domain.dto.produto.ProdutoDTO;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EnderecoRepository;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pagamentos.factories.PagamentoProcessarFactory;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoProcessar;
import com.jciterceros.vr_online_backend.domain.pagamentos.repositories.PagamentoRepository;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations.PagamentoProcessamentoService;
import com.jciterceros.vr_online_backend.domain.pedidos.models.ItemPedido;
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoVenda;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.ItemPedidoRepository;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.PedidoVendaRepository;
import com.jciterceros.vr_online_backend.domain.pessoa.repositories.PessoaRepository;
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

import java.util.ArrayList;
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

    private final PessoaRepository pessoaRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    private PagamentoProcessarFactory pagamentoProcessarFactory;

    @Autowired
    public PedidoVendaServiceImpl(ModelMapper mapper, PedidoVendaRepository pedidoVendaRepository, PagamentoRepository pagamentoRepository, PagamentoProcessamentoService pagamentoProcessamentoService, PessoaRepository pessoaRepository, ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository, EnderecoRepository enderecoRepository) {
        this.mapper = mapper;
        this.pedidoVendaRepository = pedidoVendaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoProcessamentoService = pagamentoProcessamentoService;
        this.pessoaRepository = pessoaRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
        this.enderecoRepository = enderecoRepository;
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

        PedidoVenda pedidoVenda = mapper.map(pedidoVendaDTO, PedidoVenda.class);

        // Criar um Novo Pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento(pedidoVendaDTO.getTipoPagamento());
        //TODO: Arrumar regra de calculo, Supondo que o valor total do pedido seja o valor do pagamento
        pagamento.setValor(pedidoVendaDTO.getValorTotal());
        pagamento.setData(pedidoVendaDTO.getDataPedido());

        // Obtém a estratégia de processamento de pagamento correta e processa o pagamento
        IPagamentoProcessar processar = pagamentoProcessarFactory.getPagamentoProcessar(pagamento.getTipoPagamento());
        processar.processarPagamento(pagamento);

        // Processando o pagamento
        pagamentoProcessamentoService.processarPagamento(pagamento);

        // Persiste o pagamento no banco de dados
        pedidoVenda.setPagamento(pagamentoRepository.save(pagamento));

        // Consulta o cliente no banco de dados
        pedidoVenda.setCliente(pessoaRepository.findById(pedidoVendaDTO.getCliente().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado")));

        // Cria os itens do pedido e persiste no banco de dados
        List<ItemPedido> itens = new ArrayList<>();
        pedidoVendaDTO.getItens().forEach(itemPedidoDTO -> {
            Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
            itemPedidoDTO.setProduto(mapper.map(produto, ProdutoDTO.class));

            ItemPedido itemPedido = itemPedidoRepository.save(mapper.map(itemPedidoDTO, ItemPedido.class));

            itens.add(itemPedido);
        });

        // Adicionando os itens ao pedido de venda
        pedidoVenda.setItens(itens);

        // Consulta o endereco de entrega no banco de dados
        pedidoVenda.setLocalEntrega(enderecoRepository.findById(pedidoVendaDTO.getLocalEntregaId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço de entrega não encontrado")));

        try {
            pedidoVenda = pedidoVendaRepository.save(pedidoVenda);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pedido de venda");
        }
        // Uma vez criado o pedido de venda, é necessário associar o pedido de venda aos itens do pedido
        // e atualizar o valor total do pedido de venda
        PedidoVenda finalPedidoVenda = pedidoVenda;
        finalPedidoVenda.getItens().forEach(itemPedido -> {
            itemPedido.setPedidoVenda(finalPedidoVenda);
            itemPedidoRepository.save(itemPedido);
        });

        finalPedidoVenda.setValorTotal(finalPedidoVenda.calcularValorTotal());
        // Atualizar o pedido de venda com o valor total
        pedidoVendaRepository.save(finalPedidoVenda);

        return mapper.map(pedidoVenda, PedidoVendaDTO.class);
    }

    @Override
    public PedidoVendaDTO atualizar(Long id, PedidoVendaDTO pedidoVendaDTO) {

        String error = validateFields(pedidoVendaDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        PedidoVenda pedidoVenda = pedidoVendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PEDIDO_VENDA_NAO_ENCONTRADO));

        // Atualiza o pedido de venda com os novos dados do PedidoVendaDTO
        pedidoVenda.setCpfNota(pedidoVendaDTO.getCpfNota());
        pedidoVenda.setDataPedido(pedidoVendaDTO.getDataPedido());
        pedidoVenda.setLocalEntrega(enderecoRepository.findById(pedidoVendaDTO.getLocalEntregaId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço de entrega não encontrado")));

        // Remove os itens existentes
        pedidoVenda.getItens().clear();

        // Adiciona os novos itens
        for (ItemPedidoDTO itemPedidoDTO : pedidoVendaDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
            itemPedidoDTO.setProduto(mapper.map(produto, ProdutoDTO.class));

            ItemPedido itemPedido = itemPedidoRepository.save(mapper.map(itemPedidoDTO, ItemPedido.class));

            pedidoVenda.getItens().add(itemPedido);
        }

        // Atualiza o pagamento
        Pagamento pagamento = pedidoVenda.getPagamento();
        pagamento.setTipoPagamento(pedidoVendaDTO.getTipoPagamento());
        pagamento.setValor(pedidoVendaDTO.getValorTotal());
        pagamento.setData(pedidoVendaDTO.getDataPedido());

        // Obtém a estratégia de processamento de pagamento correta e processa o pagamento
        IPagamentoProcessar processar = pagamentoProcessarFactory.getPagamentoProcessar(pagamento.getTipoPagamento());
        processar.processarPagamento(pagamento);

        // Processando o pagamento
        pagamentoProcessamentoService.processarPagamento(pagamento);

        // Persiste o pagamento no banco de dados
        pedidoVenda.setPagamento(pagamentoRepository.save(pagamento));

        // Salva o pedido de venda atualizado no banco de dados
        pedidoVenda = pedidoVendaRepository.save(pedidoVenda);

        // Uma vez atualizado o pedido de venda, é necessário associar o pedido de venda aos itens do pedido
        // e atualizar o valor total do pedido de venda
        for (ItemPedido itemPedido : pedidoVenda.getItens()) {
            itemPedido.setPedidoVenda(pedidoVenda);
            itemPedidoRepository.save(itemPedido);
        }
        pedidoVenda.setValorTotal(pedidoVenda.calcularValorTotal());

        // Atualizar o pedido de venda com o valor total
        pedidoVendaRepository.save(pedidoVenda);

        return mapper.map(pedidoVenda, PedidoVendaDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!pedidoVendaRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_VENDA_NAO_ENCONTRADO);
        }
        // deletar os itens do pedido
        pedidoVendaRepository.findById(id).ifPresent(pedidoVenda -> {
            pedidoVenda.getItens().forEach(itemPedido -> itemPedidoRepository.deleteById(itemPedido.getId()));
        });

        // deletar o pagamento
        pedidoVendaRepository.findById(id).ifPresent(pedidoVenda -> {
            pagamentoRepository.deleteById(pedidoVenda.getPagamento().getId());
        });

        // deletar o pedido de venda
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