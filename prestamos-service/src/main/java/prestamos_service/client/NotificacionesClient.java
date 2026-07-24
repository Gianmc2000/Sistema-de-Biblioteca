package prestamos_service.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import prestamos_service.dto.request.NotificacionRequest;

@Component
@RequiredArgsConstructor
public class NotificacionesClient {

    private final RestClient.Builder builder;

    private static final String SERVICE = "http://notificaciones-service";

    public void enviar(NotificacionRequest request){

        builder.build()

                .post()

                .uri(SERVICE + "/api/v1/notificaciones")

                .body(request)

                .retrieve()

                .toBodilessEntity();

    }

}