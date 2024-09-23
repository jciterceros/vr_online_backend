package com.jciterceros.vr_online_backend.domain.pagamentos.services;

import com.jciterceros.vr_online_backend.domain.pagamentos.models.IPagamentoNotificar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.IPagamentoProcessar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.IPagamentoValidar;
import com.jciterceros.vr_online_backend.domain.pagamentos.models.Pagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pagamento_boleto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoBoleto implements IPagamentoProcessar, IPagamentoValidar, IPagamentoNotificar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public void processarPagamento(Pagamento pagamento) {
        // Implementação específica para Boleto
        System.out.println("Processando pagamento via boleto...");
    }

    @Override
    public boolean validarPagamento(Pagamento pagamento) {
        // Implementação específica para Boleto
        System.out.println("Validando pagamento via boleto...");
        return true;
    }

    @Override
    public void notificarStatus(Pagamento pagamento) {
        // Implementação específica para Boleto
        System.out.println("Notificando status do pagamento via boleto...");
    }
}
