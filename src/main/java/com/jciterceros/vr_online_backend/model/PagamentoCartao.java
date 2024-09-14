package com.jciterceros.vr_online_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamento_cartao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoCartao implements IPagamentoProcessar, IPagamentoValidar, IPagamentoNotificar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public void processarPagamento(Pagamento pagamento) {
        // Implementação específica para Cartão
    }

    @Override
    public boolean validarPagamento(Pagamento pagamento) {
        // Implementação específica para Cartão
        return true;
    }

    @Override
    public void notificarStatus(Pagamento pagamento) {
        // Implementação específica para Cartão
    }
}
