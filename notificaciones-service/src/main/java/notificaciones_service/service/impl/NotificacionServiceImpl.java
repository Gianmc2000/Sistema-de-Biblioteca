package notificaciones_service.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import notificaciones_service.dto.request.EnviarNotificacionRequest;
import notificaciones_service.dto.response.NotificacionResponse;
import notificaciones_service.service.NotificacionService;

@Service
@Slf4j
public class NotificacionServiceImpl implements NotificacionService {

    @Override
    public NotificacionResponse enviar(EnviarNotificacionRequest request) {

        log.info("===============================");
        log.info("SIMULANDO ENVÍO DE NOTIFICACIÓN");
        log.info("Préstamo : {}", request.getCodigoPrestamo());
        log.info("Socio    : {}", request.getCodigoSocio());
        log.info("Correo   : {}", request.getEmail());
        log.info("Asunto   : {}", request.getAsunto());
        log.info("Mensaje  : {}", request.getMensaje());
        log.info("===============================");

        return NotificacionResponse.builder()
                .codigoPrestamo(request.getCodigoPrestamo())
                .destinatario(request.getEmail())
                .asunto(request.getAsunto())
                .estado("ENVIADA")
                .fechaEnvio(LocalDateTime.now())
                .build();

    }

}
