package libros_service.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SocioResponse {

    private String codigoSocio;
    private String nombre;
    private String email;
    private String telefono;
    private LocalDate fechaInscripcion;
    private Boolean activo;

}
