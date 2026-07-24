package libros_service.service;

import java.util.List;

import libros_service.dto.request.ActualizarEjemplarRequest;
import libros_service.dto.request.CrearEjemplarRequest;
import libros_service.dto.response.EjemplarResponse;

public interface EjemplarService {

    EjemplarResponse registrar(CrearEjemplarRequest request);

    List<EjemplarResponse> listar();

    EjemplarResponse buscarPorCodigo(String codigoEjemplar);

    EjemplarResponse actualizar(String codigo,ActualizarEjemplarRequest request);

    void eliminar(String codigoEjemplar);

    EjemplarResponse actualizarDisponibilidad(String codigoEjemplar, Boolean disponible);

}
