package com.jciterceros.vr_online_backend.domain.pagamentos.models;

public interface IPagamentoProcessar {
    void processarPagamento(Pagamento pagamento);
}
