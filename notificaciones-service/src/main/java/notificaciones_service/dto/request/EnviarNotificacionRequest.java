package notificaciones_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class EnviarNotificacionRequest {

    @NotBlank(message = "El código del préstamo es obligatorio")
    private String codigoPrestamo;

    @NotBlank(message = "El código del socio es obligatorio")
    private String codigoSocio;

    @Email(message = "Correo electrónico inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

}
