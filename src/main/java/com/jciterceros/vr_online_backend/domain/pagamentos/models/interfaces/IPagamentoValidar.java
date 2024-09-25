package com.jciterceros.vr_online_backend.domain.pagamentos.models.interfaces;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;

public interface IPagamentoValidar {
    boolean validarPagamento(Pagamento pagamento);
}
