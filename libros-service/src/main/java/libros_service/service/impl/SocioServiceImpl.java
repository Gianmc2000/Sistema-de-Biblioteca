package libros_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import libros_service.dto.request.ActualizarSocioRequest;
import libros_service.dto.request.CrearSocioRequest;
import libros_service.dto.response.SocioResponse;
import libros_service.entity.Socio;
import libros_service.exception.SocioNoEncontradoException;
import libros_service.mapper.SocioMapper;
import libros_service.repository.SocioRepository;
import libros_service.service.SocioService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SocioServiceImpl implements SocioService {

    private final SocioRepository repository;
    private final SocioMapper mapper;

    @Override
    public SocioResponse registrar(CrearSocioRequest request) {

        Socio entity = mapper.toEntity(request);

        repository.save(entity);

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public SocioResponse buscarPorCodigo(String codigoSocio) {

        Socio entity = repository.findById(codigoSocio)
                .orElseThrow(() ->
                        new SocioNoEncontradoException(codigoSocio));

        return mapper.toResponse(entity);

    }

    @Override
    public SocioResponse actualizar(String codigoSocio,
                                    ActualizarSocioRequest request) {

        Socio entity = repository.findById(codigoSocio)
                .orElseThrow(() ->
                        new SocioNoEncontradoException(codigoSocio));

        mapper.actualizarEntidad(entity, request);

        repository.save(entity);

        return mapper.toResponse(entity);

    }

    @Override
    public void eliminar(String codigoSocio) {

        Socio entity = repository.findById(codigoSocio)
                .orElseThrow(() ->
                        new SocioNoEncontradoException(codigoSocio));

        repository.delete(entity);

    }

}