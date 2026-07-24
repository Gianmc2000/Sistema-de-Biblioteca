package libros_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearEjemplarRequest {

    @NotBlank(message = "El código es obligatorio")
    private String codigoEjemplar;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @NotNull(message = "El año de publicación es obligatorio")
    @Positive
    private Integer anoPublicacion;

    @NotNull
    private Boolean disponible;

}