package prestamos_service.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionRequest {

    private String codigoPrestamo;

    private String codigoSocio;

    private String email;

    private String asunto;

    private String mensaje;

}
