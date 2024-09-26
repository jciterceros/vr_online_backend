package com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations;

import com.jciterceros.vr_online_backend.domain.pagamentos.factories.PagamentoNotificarFactory;
import com.jciterceros.vr_online_backend.domain.pagamentos.factories.PagamentoProcessarFactory;
import com.jciterceros.vr_online_backend.domain.pagamentos.factories.PagamentoValidarFactory;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.enums.StatusPagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoNotificar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoProcessar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoValidar;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PagamentoProcessamentoService {
    private final PagamentoValidarFactory pagamentoValidarFactory;
    private final PagamentoProcessarFactory pagamentoProcessarFactory;
    private final PagamentoNotificarFactory pagamentoNotificarFactory;

    @Autowired
    public PagamentoProcessamentoService(PagamentoValidarFactory pagamentoValidarFactory, PagamentoProcessarFactory pagamentoProcessarFactory, PagamentoNotificarFactory pagamentoNotificarFactory) {
        this.pagamentoValidarFactory = pagamentoValidarFactory;
        this.pagamentoProcessarFactory = pagamentoProcessarFactory;
        this.pagamentoNotificarFactory = pagamentoNotificarFactory;
    }

    @Transactional
    public void processarPagamento(Pagamento pagamento) {

        // Obtém as estratégias corretas com base no TipoPagamento
        IPagamentoValidar validar = pagamentoValidarFactory.getPagamentoValidar(pagamento.getTipoPagamento());
        IPagamentoProcessar processar = pagamentoProcessarFactory.getPagamentoProcessar(pagamento.getTipoPagamento());
        IPagamentoNotificar notificar = pagamentoNotificarFactory.getPagamentoNotificar(pagamento.getTipoPagamento());

        if (validar.validarPagamento(pagamento)) {
            processar.processarPagamento(pagamento);
            pagamento.setStatus(StatusPagamento.CONFIRMADO);
            pagamento.setDataConfirmacao(LocalDate.now());
            notificar.notificarStatus(pagamento);
        } else {
            pagamento.setStatus(StatusPagamento.CANCELADO);
            throw new IllegalArgumentException("Pagamento inválido.");
        }
    }
}
