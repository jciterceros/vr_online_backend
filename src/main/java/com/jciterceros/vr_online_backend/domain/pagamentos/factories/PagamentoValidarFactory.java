package com.jciterceros.vr_online_backend.domain.pagamentos.factories;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.enums.TipoPagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoValidar;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations.PagamentoBitcoins;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations.PagamentoBoleto;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations.PagamentoCartao;
import com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations.PagamentoPIX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoValidarFactory {
    private final PagamentoPIX pagamentoPIX;
    private final PagamentoBoleto pagamentoBoleto;
    private final PagamentoCartao pagamentoCartao;
    private final PagamentoBitcoins pagamentoBitcoins;

    @Autowired
    public PagamentoValidarFactory(PagamentoPIX pagamentoPIX, PagamentoBoleto pagamentoBoleto, PagamentoCartao pagamentoCartao, PagamentoBitcoins pagamentoBitcoins) {
        this.pagamentoPIX = pagamentoPIX;
        this.pagamentoBoleto = pagamentoBoleto;
        this.pagamentoCartao = pagamentoCartao;
        this.pagamentoBitcoins = pagamentoBitcoins;
    }

    public IPagamentoValidar getPagamentoValidar(TipoPagamento tipoPagamento) {
        switch (tipoPagamento) {
            case PIX:
                return pagamentoPIX;
            case BOLETO:
                return pagamentoBoleto;
            case CARTAO:
                return pagamentoCartao;
            case BITCOINS:
                return pagamentoBitcoins;
            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido");
        }
    }
}
