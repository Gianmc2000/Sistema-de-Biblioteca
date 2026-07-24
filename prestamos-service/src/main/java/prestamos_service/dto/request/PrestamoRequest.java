package prestamos_service.dto.request;

import lombok.Data;

@Data
public class PrestamoRequest {
    private String codigoEjemplar;
    private String codigoSocio;
    private int dias;
}