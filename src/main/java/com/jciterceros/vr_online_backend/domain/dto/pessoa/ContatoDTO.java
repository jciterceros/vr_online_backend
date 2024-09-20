package com.jciterceros.vr_online_backend.domain.dto.pessoa;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.exception.handler.MethodArgumentNotValidException;
import com.jciterceros.vr_online_backend.domain.pessoa.models.TipoPessoa;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {
    private Long id;

    @NotNull(message = "Pessoa é obrigatória")
    private PessoaDTO pessoa;

    @NotNull(message = "Endereços são obrigatórios")
    private List<EnderecoDTO> enderecos;

    @NotNull(message = "Telefones são obrigatórios")
    private List<TelefoneDTO> telefones;

    // Mostrar pessoaFisica e pessoaJuridica
    @Autowired
    public ContatoDTO(PessoaDTO pessoa, List<EnderecoDTO> enderecos, List<TelefoneDTO> telefones) {
        this.pessoa = pessoa;
        this.enderecos = enderecos;
        this.telefones = telefones;
        assignContatoDTO();
    }

    public void assignContatoDTO() {
        if (pessoa.getTipo() == TipoPessoa.FISICA) {
            if (pessoa.getPessoaFisica() == null) {
                throw new MethodArgumentNotValidException("Pessoa física não informada");
            }
            pessoa.setPessoaFisica(new PessoaFisicaDTO());
        } else {
            if (pessoa.getPessoaJuridica() == null) {
                throw new MethodArgumentNotValidException("Pessoa jurídica não informada");
            }
            pessoa.setPessoaJuridica(new PessoaJuridicaDTO());
        }
    }
}
