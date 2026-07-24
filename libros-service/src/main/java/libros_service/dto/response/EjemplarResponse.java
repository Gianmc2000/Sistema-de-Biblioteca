package libros_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EjemplarResponse {

    private String codigoEjemplar;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anioPublicacion;
    private Boolean disponible;

}
