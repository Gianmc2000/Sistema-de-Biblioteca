package libros_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import libros_service.dto.request.ActualizarEjemplarRequest;
import libros_service.dto.request.CrearEjemplarRequest;
import libros_service.dto.response.EjemplarResponse;
import libros_service.entity.Ejemplar;
import libros_service.exception.EjemplarNoEncontradoException;
import libros_service.mapper.EjemplarMapper;
import libros_service.repository.EjemplarRepository;
import libros_service.service.EjemplarService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EjemplarServiceImpl
        implements EjemplarService {

    private final EjemplarRepository repository;

    private final EjemplarMapper mapper;

    @Override
    public EjemplarResponse registrar(CrearEjemplarRequest request) {

        Ejemplar entity = mapper.toEntity(request);
        repository.save(entity);
        return mapper.toResponse(entity);

    }

    @Override
    public List<EjemplarResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

    @Override
    public EjemplarResponse buscarPorCodigo(String codigo) {

        Ejemplar entity = repository.findById(codigo)
                .orElseThrow(() -> new EjemplarNoEncontradoException(codigo));
        return mapper.toResponse(entity);

    }

    @Override
    public EjemplarResponse actualizar(String codigo, ActualizarEjemplarRequest request) {

        Ejemplar entity = repository.findById(codigo)
                .orElseThrow(() -> new EjemplarNoEncontradoException(codigo));

        mapper.actualizarEntidad(entity, request);
        repository.save(entity);
        return mapper.toResponse(entity);

    }

    @Override
    public void eliminar(String codigo) {

        Ejemplar entity = repository.findById(codigo)
                .orElseThrow(() -> new EjemplarNoEncontradoException(codigo));

        repository.delete(entity);

    }

    @Override
    public EjemplarResponse actualizarDisponibilidad(
            String codigo,
            Boolean disponible) {

        Ejemplar entity = repository.findById(codigo)
                .orElseThrow(() -> new EjemplarNoEncontradoException(codigo));

        entity.setDisponible(disponible);

        repository.save(entity);

        return mapper.toResponse(entity);

    }

}
