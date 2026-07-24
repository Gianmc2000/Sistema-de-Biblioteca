package prestamos_service.dto.response;


import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComprobantePrestamoResponse {

    private String codigoPrestamo;

    private String codigoEjemplar;

    private String codigoSocio;

    private LocalDateTime fechaPrestamo;

    private LocalDate fechaDevolucionEsperada;

    private String estado;

    private String mensaje;

}
