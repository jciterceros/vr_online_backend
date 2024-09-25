package com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;

public interface IPagamentoNotificar {
    void notificarStatus(Pagamento pagamento);
}
