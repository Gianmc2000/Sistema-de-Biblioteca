package libros_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import libros_service.dto.request.ActualizarSocioRequest;
import libros_service.dto.request.CrearSocioRequest;
import libros_service.dto.response.SocioResponse;


public interface SocioService {

    SocioResponse registrar(CrearSocioRequest request);

    List<SocioResponse> listar();

    SocioResponse buscarPorCodigo(String codigoSocio);

    SocioResponse actualizar(String codigoSocio,ActualizarSocioRequest request);

    void eliminar(String codigoSocio);

}