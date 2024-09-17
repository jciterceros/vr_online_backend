package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.endereco.adapters.EnderecoViaCepAdapter;
import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.endereco.models.Municipio;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EnderecoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.MunicipioRepository;
import com.jciterceros.vr_online_backend.domain.exception.DatabaseException;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import com.jciterceros.vr_online_backend.domain.exception.handler.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    public static final String ENDERECO_NAO_ENCONTRADO = "Endereco não encontrado";
    private final ModelMapper mapper;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    private final EnderecoRepository enderecoRepository;
    private final MunicipioRepository municipioRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    public EnderecoServiceImpl(ModelMapper mapper, EnderecoRepository enderecoRepository, MunicipioRepository municipioRepository) {
        this.mapper = mapper;
        this.enderecoRepository = enderecoRepository;
        this.municipioRepository = municipioRepository;
        configureMapper();
    }

    @Override
    public String validateFields(EnderecoDTO enderecoDTO) {
        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);

        return violations.isEmpty() ? null : violations.iterator().next().getMessage();
    }

    @Override
    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.findAll().stream()
                .map(endereco -> mapper.map(endereco, EnderecoDTO.class))
                .toList();
    }

    @Override
    public Optional<EnderecoDTO> buscarPorId(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO);
        }
        return enderecoRepository.findById(id)
                .map(endereco -> mapper.map(endereco, EnderecoDTO.class));
    }

    @Override
    public EnderecoDTO salvar(EnderecoDTO enderecoDTO) {
        String error = validateFields(enderecoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }

        Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
        try {
            endereco = enderecoRepository.save(endereco);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao salvar o endereço");
        }

        return mapper.map(endereco, EnderecoDTO.class);
    }

    @Override
    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO) {
        String error = validateFields(enderecoDTO);
        if (error != null) {
            throw new DatabaseException(error);
        }
        if (id == null) {
            throw new MethodArgumentNotValidException("Id não pode ser nulo");
        }

        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO);
        }

        Municipio municipio = municipioRepository.findById(enderecoDTO.getMunicipio().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado"));

        Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
        endereco.setId(id);
        endereco.setMunicipio(municipio);
        try {
            endereco = enderecoRepository.save(endereco);
        } catch (DatabaseException e) {
            throw new DatabaseException("Erro ao atualizar o endereço");
        }

        return mapper.map(endereco, EnderecoDTO.class);
    }

    @Override
    public EnderecoDTO converterParaEndereco(String cep, Integer numero) {
        EnderecoViaCepAdapter enderecoViaCepAdapter = new EnderecoViaCepAdapter(mapper, municipioRepository, viaCepService);
        cep = cep.replace("-", "");
        if (cep.length() != 8) {
            throw new ResourceNotFoundException("CEP inválido");
        }
        return enderecoViaCepAdapter.converterParaEndereco(cep, numero);
    }

    @Override
    public MunicipioDTO buscarMunicipioPorId(Long id) {
        return municipioRepository.findById(id)
                .map(municipio -> mapper.map(municipio, MunicipioDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado"));
    }

    @Override
    public void deletar(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENDERECO_NAO_ENCONTRADO);
        }
        enderecoRepository.deleteById(id);
    }

    public void configureMapper() {
        mapper.addMappings(new PropertyMap<EnderecoDTO, Endereco>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getContato());
            }
        });
    }
}
