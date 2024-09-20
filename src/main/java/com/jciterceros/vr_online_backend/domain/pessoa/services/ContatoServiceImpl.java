package com.jciterceros.vr_online_backend.domain.pessoa.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.pessoa.ContatoDTO;
import com.jciterceros.vr_online_backend.domain.dto.pessoa.TelefoneDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EnderecoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.services.EnderecoServiceImpl;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Contato;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Pessoa;
import com.jciterceros.vr_online_backend.domain.pessoa.models.Telefone;
import com.jciterceros.vr_online_backend.domain.pessoa.repositories.ContatoRepository;
import com.jciterceros.vr_online_backend.domain.pessoa.repositories.PessoaRepository;
import com.jciterceros.vr_online_backend.domain.pessoa.repositories.TelefoneRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContatoServiceImpl implements ContatoService {

    public static final String CONTATO_NAO_ENCONTRADO = "Contato não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final ContatoRepository contatoRepository;

    private final EnderecoServiceImpl enderecoService;

    private final TelefoneServiceImpl telefoneService;

    private final EnderecoRepository enderecoRepository;

    private final TelefoneRepository telefoneRepository;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public ContatoServiceImpl(ModelMapper mapper, ContatoRepository contatoRepository, EnderecoServiceImpl enderecoService, TelefoneServiceImpl telefoneService, EnderecoRepository enderecoRepository, TelefoneRepository telefoneRepository, PessoaRepository pessoaRepository) {
        this.mapper = mapper;
        this.contatoRepository = contatoRepository;
        this.enderecoService = enderecoService;
        this.telefoneService = telefoneService;
        this.enderecoRepository = enderecoRepository;
        this.telefoneRepository = telefoneRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public String validateFields(ContatoDTO contatoDTO) {
        Set<ConstraintViolation<ContatoDTO>> violations = validator.validate(contatoDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<ContatoDTO> listarTodos() {

        List<Contato> contatos = contatoRepository.findAll();

        return contatos.stream()
                .map(contato -> {
                    ContatoDTO contatoDTO = mapper.map(contato, ContatoDTO.class);

                    List<EnderecoDTO> enderecosDTO = contato.getEnderecos().stream()
                            .map(endereco -> enderecoRepository.findById(endereco.getId())
                                    .map(e -> mapper.map(e, EnderecoDTO.class))
                                    .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado: " + endereco.getId())))
                            .collect(Collectors.toList());
                    contatoDTO.setEnderecos(enderecosDTO);

                    List<TelefoneDTO> telefonesDTO = contato.getTelefones().stream()
                            .map(telefone -> telefoneRepository.findById(telefone.getId())
                                    .map(t -> mapper.map(t, TelefoneDTO.class))
                                    .orElseThrow(() -> new ResourceNotFoundException("Telefone não encontrado: " + telefone.getId())))
                            .collect(Collectors.toList());
                    contatoDTO.setTelefones(telefonesDTO);

                    return contatoDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ContatoDTO> buscarPorId(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO);
        }
        return contatoRepository.findById(id)
                .map(contato -> mapper.map(contato, ContatoDTO.class));
    }

    @Override
    public ContatoDTO salvar(ContatoDTO contatoDTO) {
        String error = validateFields(contatoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Pessoa pessoa = pessoaRepository.findById(contatoDTO.getPessoa().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada"));

        Contato contato = mapper.map(contatoDTO, Contato.class);
        contato.setPessoa(pessoa);

        try {
            contato = contatoRepository.save(contato);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao salvar contato");
        }

        List<Endereco> enderecos = enderecoService.salvarLista(contato.getId(), contatoDTO.getEnderecos());
        List<Telefone> telefones = telefoneService.salvarLista(contato.getId(), contatoDTO.getTelefones());

        contato.setEnderecos(enderecos);
        contato.setTelefones(telefones);

        return mapper.map(contato, ContatoDTO.class);
    }

    @Override
    public ContatoDTO atualizar(Long id, ContatoDTO contatoDTO) {
        String error = validateFields(contatoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO);
        }

        Contato contato = mapper.map(contatoDTO, Contato.class);
        contato.setId(id);

        try {
            contato = contatoRepository.save(contato);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao atualizar contato");
        }

        return mapper.map(contato, ContatoDTO.class);
    }

    @Override
    public void deletar(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException(CONTATO_NAO_ENCONTRADO);
        }
        contatoRepository.deleteById(id);
    }
}