package com.jciterceros.vr_online_backend.domain.endereco.services;

import com.jciterceros.vr_online_backend.domain.dto.endereco.EnderecoDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.MunicipioDTO;
import com.jciterceros.vr_online_backend.domain.dto.endereco.ViaCepDTO;
import com.jciterceros.vr_online_backend.domain.endereco.models.Endereco;
import com.jciterceros.vr_online_backend.domain.endereco.adapters.EnderecoViaCepAdapter;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EnderecoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.EstadoRepository;
import com.jciterceros.vr_online_backend.domain.endereco.repositories.MunicipioRepository;
import com.jciterceros.vr_online_backend.domain.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final ModelMapper mapper;
    private final EnderecoRepository enderecoRepository;
    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public EnderecoServiceImpl(ModelMapper mapper, EnderecoRepository enderecoRepository, MunicipioRepository municipioRepository, EstadoRepository estadoRepository) {
        this.mapper = mapper;
        this.enderecoRepository = enderecoRepository;
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
        configureMapper();
    }

    @Override
    public EnderecoDTO salvar(EnderecoDTO enderecoDTO) {
        Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
        endereco = enderecoRepository.save(endereco);

        return mapper.map(endereco, EnderecoDTO.class);
    }

    @Override
    public Optional<EnderecoDTO> buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(endereco -> mapper.map(endereco, EnderecoDTO.class));
    }

    @Override
    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.findAll().stream()
                .map(endereco -> mapper.map(endereco, EnderecoDTO.class))
                .toList();
    }

    @Override
    public void deletar(Long id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTO) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereco não encontrado");
        }
        Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
        endereco = enderecoRepository.save(endereco);

        return mapper.map(endereco, EnderecoDTO.class);
    }

    @Override
    public EnderecoDTO converterParaEndereco(ViaCepDTO viaCepDTO, Integer numero) {
        EnderecoDTO enderecoDTO = new EnderecoViaCepAdapter(mapper, municipioRepository,estadoRepository).converterParaEndereco(viaCepDTO);
//        EnderecoDTO enderecoDTO = new EnderecoViaCepAdapter(municipioRepository,estadoRepository).converterParaEndereco(viaCepDTO);
        enderecoDTO.setNumero(numero);
        return enderecoDTO;
    }

    @Override
    public MunicipioDTO buscarMunicipioPorId(Long id) {
        return municipioRepository.findById(id)
                .map(municipio -> mapper.map(municipio, MunicipioDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Município não encontrado"));
    }

    public void configureMapper() {
        mapper.typeMap(EnderecoDTO.class, Endereco.class).addMappings(mapping -> {
            mapping.skip(Endereco::setId);
            mapping.skip(Endereco::setContatoService);
        });
    }
}
