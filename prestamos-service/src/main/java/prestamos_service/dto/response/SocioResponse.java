package prestamos_service.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocioResponse {

    private String codigoSocio;

    private String nombre;

    private String email;

    private String telefono;

    private LocalDate fechaInscripcion;

    private Boolean activo;

}