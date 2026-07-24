package prestamos_service.factory;

import org.springframework.stereotype.Component;

@Component
public class MensajeNotificacionFactory {

    public String crearMensaje(String estado, String codigoEjemplar, String codigoSocio) {
        return switch (estado) {
            case "REGISTRADA" -> "Estimado socio " + codigoSocio + ", su préstamo del ejemplar " + codigoEjemplar + " fue registrado con éxito.";
            case "DEVUELTO" -> "El ejemplar " + codigoEjemplar + " devuelto por el socio " + codigoSocio + " ha sido procesado correctamente.";
            case "RECHAZADA" -> "Su solicitud de préstamo para el ejemplar " + codigoEjemplar + " fue RECHAZADA.";
            default -> "Notificación sobre el préstamo.";
        };
    }
}