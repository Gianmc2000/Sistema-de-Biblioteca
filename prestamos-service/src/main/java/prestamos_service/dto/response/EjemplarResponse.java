package prestamos_service.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EjemplarResponse {

    private String codigoEjemplar;

    private String titulo;

    private String autor;

    private String isbn;

    private Integer anioPublicacion;

    private Boolean disponible;

}
