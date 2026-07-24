package libros_service.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearSocioRequest {

    @NotBlank( message = "El código del socio no puede estar vacío")
    private String codigoSocio;

    @NotBlank( message = "El nombre del socio no puede estar vacío")
    private String nombre;

    @Email
    @NotBlank( message = "El email del socio no puede estar vacío")
    private String email;

    @NotBlank( message = "El teléfono del socio no puede estar vacío")
    private String telefono;

}