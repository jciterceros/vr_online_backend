package com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;

public interface IPagamentoProcessar {
    void processarPagamento(Pagamento pagamento);
}
