package com.jciterceros.vr_online_backend.domain.pedidos.services;

import com.jciterceros.vr_online_backend.domain.dto.pedido.ItemPedidoDTO;
import com.jciterceros.vr_online_backend.domain.dto.pedido.PedidoCompraDTO;
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
import com.jciterceros.vr_online_backend.domain.pedidos.models.PedidoCompra;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.ItemPedidoRepository;
import com.jciterceros.vr_online_backend.domain.pedidos.repositories.PedidoCompraRepository;
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
public class PedidoCompraServiceImpl implements PedidoCompraService {

    public static final String PEDIDO_COMPRA_NAO_ENCONTRADO = "Pedido de compra não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final PedidoCompraRepository pedidoCompraRepository;

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoProcessamentoService pagamentoProcessamentoService;
    private final PessoaRepository pessoaRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    private PagamentoProcessarFactory pagamentoProcessarFactory;

    @Autowired
    public PedidoCompraServiceImpl(ModelMapper mapper, PedidoCompraRepository pedidoCompraRepository, PagamentoRepository pagamentoRepository, PagamentoProcessamentoService pagamentoProcessamentoService, PessoaRepository pessoaRepository, ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository, EnderecoRepository enderecoRepository) {
        this.mapper = mapper;
        this.pedidoCompraRepository = pedidoCompraRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoProcessamentoService = pagamentoProcessamentoService;
        this.pessoaRepository = pessoaRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
        this.enderecoRepository = enderecoRepository;
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

        // Criar um Novo Pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setTipoPagamento(pedidoCompraDTO.getTipoPagamento());
        // TODO: Arrumar regra de calculo
        pagamento.setValor(pedidoCompraDTO.getValorTotal());
        pagamento.setData(pedidoCompraDTO.getDataPedido());

        // Obtém a estratégia de processamento de pagamento correta e processa o pagamento
        IPagamentoProcessar processar = pagamentoProcessarFactory.getPagamentoProcessar(pagamento.getTipoPagamento());
        processar.processarPagamento(pagamento);

        // Processando o pagamento
        pagamentoProcessamentoService.processarPagamento(pagamento);

        // Persiste o pagamento no banco de dados
        pedidoCompra.setPagamento(pagamentoRepository.save(pagamento));

        // Consulta o comprador no banco de dados
        pedidoCompra.setComprador(pessoaRepository.findById(pedidoCompraDTO.getComprador().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comprador não encontrado")));

        // Consulta o fornecedor no banco de dados
        pedidoCompra.setFornecedor(pessoaRepository.findById(pedidoCompraDTO.getFornecedor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado")));

        // Cria os itens do pedido e persiste no banco de dados
        List<ItemPedido> itens = new ArrayList<>();
        pedidoCompraDTO.getItens().forEach(itemPedidoDTO -> {
            Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
            itemPedidoDTO.setProduto(mapper.map(produto, ProdutoDTO.class));

            ItemPedido itemPedido = itemPedidoRepository.save(mapper.map(itemPedidoDTO, ItemPedido.class));

            itens.add(itemPedido);
        });

        // Adicionando os itens ao pedido de compra
        pedidoCompra.setItens(itens);

        // Consulta o endereço de entrega no banco de dados
        pedidoCompra.setLocalEntrega(enderecoRepository.findById(pedidoCompraDTO.getLocalEntregaId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço de entrega não encontrado")));

        try {
            pedidoCompra = pedidoCompraRepository.save(pedidoCompra);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar pedido de compra");
        }
        // Uma vez criado o pedido de compra, é necessário associar o pedido de compra aos itens do pedido
        // e atualizar o valor total do pedido de compra
        PedidoCompra finalPedidoCompra = pedidoCompra;
        pedidoCompra.getItens().forEach(itemPedido -> {
            itemPedido.setPedidoCompra(finalPedidoCompra);
            itemPedidoRepository.save(itemPedido);
        });

        finalPedidoCompra.setValorTotal(finalPedidoCompra.calcularValorTotal());
        // Atualiza o pedido de compra com o valor total
        pedidoCompraRepository.save(finalPedidoCompra);

        return mapper.map(pedidoCompra, PedidoCompraDTO.class);
    }

    @Override
    public PedidoCompraDTO atualizar(Long id, PedidoCompraDTO pedidoCompraDTO) {
        String error = validateFields(pedidoCompraDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        PedidoCompra pedidoCompra = pedidoCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PEDIDO_COMPRA_NAO_ENCONTRADO));

        // Consulta e atualiza o comprador
        pedidoCompra.setComprador(pessoaRepository.findById(pedidoCompraDTO.getComprador().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comprador não encontrado")));

        // Consulta e atualiza o fornecedor
        pedidoCompra.setFornecedor(pessoaRepository.findById(pedidoCompraDTO.getFornecedor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor não encontrado")));

        // Atualiza o pedido de venda com os novos dados do PedidoCompraDTO
        pedidoCompra.setDataPedido(pedidoCompraDTO.getDataPedido());
        pedidoCompra.setLocalEntrega(enderecoRepository.findById(pedidoCompraDTO.getLocalEntregaId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço de entrega não encontrado")));

        // Remove os itens existentes
        pedidoCompra.getItens().clear();

        // Adiciona os novos itens
        for (ItemPedidoDTO itemPedidoDTO : pedidoCompraDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemPedidoDTO.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
            itemPedidoDTO.setProduto(mapper.map(produto, ProdutoDTO.class));

            ItemPedido itemPedido = itemPedidoRepository.save(mapper.map(itemPedidoDTO, ItemPedido.class));

            pedidoCompra.getItens().add(itemPedido);
        }

        // Atualiza o pagamento
        Pagamento pagamento = pedidoCompra.getPagamento();
        pagamento.setTipoPagamento(pedidoCompraDTO.getTipoPagamento());
        pagamento.setValor(pedidoCompraDTO.getValorTotal());
        pagamento.setData(pedidoCompraDTO.getDataPedido());

        // Obtém a estratégia de processamento de pagamento correta e processa o pagamento
        IPagamentoProcessar processar = pagamentoProcessarFactory.getPagamentoProcessar(pagamento.getTipoPagamento());
        processar.processarPagamento(pagamento);

        // Processando o pagamento
        pagamentoProcessamentoService.processarPagamento(pagamento);

        // Persiste o pagamento no banco de dados
        pedidoCompra.setPagamento(pagamentoRepository.save(pagamento));

        // Salva o pedido de compra atualizado no banco de dados
        pedidoCompra = pedidoCompraRepository.save(pedidoCompra);

        // Uma vez criado o pedido de compra, é necessário associar o pedido de compra aos itens do pedido
        // e atualizar o valor total do pedido de compra
        for (ItemPedido itemPedido : pedidoCompra.getItens()) {
            itemPedido.setPedidoCompra(pedidoCompra);
            itemPedidoRepository.save(itemPedido);
        }
        pedidoCompra.setValorTotal(pedidoCompra.calcularValorTotal());

        // Atualizar o valor total do pedido de compra
        pedidoCompraRepository.save(pedidoCompra);

        return mapper.map(pedidoCompra, PedidoCompraDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!pedidoCompraRepository.existsById(id)) {
            throw new ResourceNotFoundException(PEDIDO_COMPRA_NAO_ENCONTRADO);
        }
        // deletar os itens do pedido
        pedidoCompraRepository.findById(id).ifPresent(pedidoCompra -> {
            pedidoCompra.getItens().forEach(itemPedido -> itemPedidoRepository.deleteById(itemPedido.getId()));
        });

        // deletar o pagamento
        pedidoCompraRepository.findById(id).ifPresent(pedidoCompra -> {
            pagamentoRepository.deleteById(pedidoCompra.getPagamento().getId());
        });

        // deletar o pedido de compra
        pedidoCompraRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<PedidoCompraDTO, PedidoCompra>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                map().getLocalEntrega().setId(source.getLocalEntregaId());
            }
        });
    }
}