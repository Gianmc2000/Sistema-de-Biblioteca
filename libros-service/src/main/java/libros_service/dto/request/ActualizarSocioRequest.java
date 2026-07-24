package libros_service.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarSocioRequest {

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Boolean activo;

}