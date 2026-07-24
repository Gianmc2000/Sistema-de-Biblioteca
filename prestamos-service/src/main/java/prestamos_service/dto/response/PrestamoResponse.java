package prestamos_service.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoResponse {

    private String codigoPrestamo;

    private String codigoEjemplar;

    private String codigoSocio;

    private LocalDateTime fechaPrestamo;

    private LocalDate fechaDevolucionEsperada;

    private LocalDateTime fechaDevolucionReal;

    private String estado;

    private String motivoRechazo;

}
