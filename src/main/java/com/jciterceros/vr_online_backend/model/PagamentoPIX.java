package com.jciterceros.vr_online_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamento_pix")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoPIX implements IPagamentoProcessar, IPagamentoValidar, IPagamentoNotificar{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
