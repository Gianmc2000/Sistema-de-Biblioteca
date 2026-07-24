package notificaciones_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notificaciones_service.dto.request.EnviarNotificacionRequest;
import notificaciones_service.dto.response.ApiResponse;
import notificaciones_service.dto.response.NotificacionResponse;
import notificaciones_service.service.NotificacionService;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificacionResponse>> enviar(@Valid @RequestBody EnviarNotificacionRequest request){

        NotificacionResponse response = service.enviar(request);

        return ResponseEntity.ok(
                ApiResponse.<NotificacionResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Notificación procesada correctamente.")
                        .data(response)
                        .build());

    }

}