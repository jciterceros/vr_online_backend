package com.jciterceros.vr_online_backend.domain.pagamentos.services.implementations;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoNotificar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoProcessar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces.IPagamentoValidar;
import org.springframework.stereotype.Service;

@Service
public class PagamentoPIX implements IPagamentoProcessar, IPagamentoValidar, IPagamentoNotificar {

    @Override
    public void processarPagamento(Pagamento pagamento) {
        // Implementação específica para PIX
    }

    @Override
    public boolean validarPagamento(Pagamento pagamento) {
        // Implementação específica para PIX
        return true;
    }

    @Override
    public void notificarStatus(Pagamento pagamento) {
        // Implementação específica para PIX
    }
}
