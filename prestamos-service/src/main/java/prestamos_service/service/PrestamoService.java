package prestamos_service.service;


import java.util.List;

import prestamos_service.dto.request.RegistrarDevolucionRequest;
import prestamos_service.dto.request.RegistrarPrestamoRequest;
import prestamos_service.dto.response.ComprobantePrestamoResponse;
import prestamos_service.dto.response.PrestamoResponse;

public interface PrestamoService {

    ComprobantePrestamoResponse registrarPrestamo(
            RegistrarPrestamoRequest request);

    PrestamoResponse registrarDevolucion(
            RegistrarDevolucionRequest request);

    List<PrestamoResponse> listar();

    PrestamoResponse buscar(String codigoPrestamo);

}
