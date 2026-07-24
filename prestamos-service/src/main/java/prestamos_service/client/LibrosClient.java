
package prestamos_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import prestamos_service.dto.request.DisponibilidadRequest;
import prestamos_service.dto.response.ApiResponse;
import prestamos_service.dto.response.EjemplarResponse;
import prestamos_service.dto.response.SocioResponse;

@Component
@RequiredArgsConstructor
public class LibrosClient {

    private final RestClient.Builder builder;

    private static final String SERVICE = "http://libros-service";

    public ApiResponse<EjemplarResponse> obtenerEjemplar(String codigo){

        return builder.build()
                .get()
                .uri(SERVICE + "/api/v1/libros/{codigo}",codigo)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<EjemplarResponse>>() {});
    }

    public ApiResponse<SocioResponse> obtenerSocio(String codigo){

        return builder.build()
                .get()
                .uri(SERVICE + "/api/v1/socios/{codigo}",codigo)
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<SocioResponse>>() {});
    }

    public ApiResponse<EjemplarResponse> actualizarDisponibilidad(String codigo,Boolean disponible){

        return builder.build()
                .patch()
                .uri(SERVICE + "/api/v1/libros/{codigo}/disponibilidad",codigo)
                .body(DisponibilidadRequest.builder()
                .disponible(disponible)
                .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<EjemplarResponse>>() {});
    }

}
