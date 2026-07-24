package notificaciones_service.service;

import notificaciones_service.dto.request.EnviarNotificacionRequest;
import notificaciones_service.dto.response.NotificacionResponse;

public interface NotificacionService {

    NotificacionResponse enviar(EnviarNotificacionRequest request);

}