package prestamos_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrarPrestamoRequest {

    @NotBlank( message = "El código del préstamo no puede estar vacío")
    private String codigoPrestamo;

    @NotBlank( message = "El código del ejemplar no puede estar vacío")
    private String codigoEjemplar;

    @NotBlank( message = "El código del socio no puede estar vacío")
    private String codigoSocio;

}
