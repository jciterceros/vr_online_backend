package com.jciterceros.vr_online_backend.domain.dto.pedido;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.pagamento.PagamentoDTO;
import com.jciterceros.vr_online_backend.domain.dto.pessoa.PessoaDTO;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.enums.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PedidoVendaDTO {
    private Long id;

    private PessoaDTO cliente;

    @NotNull(message = "CPF da Nota é obrigatório")
    private String cpfNota;

    private List<ItemPedidoDTO> itens;
//    private List<Long> itensIds;

    private BigDecimal valorTotal;

    private LocalDate dataPedido;

    @NotNull(message = "Tipo de pagamento é obrigatório")
    private TipoPagamento tipoPagamento;

    private PagamentoDTO pagamento;

    private LocalDate dataEntrega;

    @NotNull(message = "Local de entrega é obrigatório")
    private Long localEntregaId;

    private EnderecoDTO localEntrega;
}