package prestamos_service.factory;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PrestamoFactory {

    private final PrestamoNormalProcessor prestamoNormalProcessor;

    private final DevolucionProcessor devolucionProcessor;

    public PrestamoProcessor obtenerProcesador(String tipo){

        return switch (tipo){
            case "PRESTAMO" -> prestamoNormalProcessor;
            case "DEVOLUCION" -> devolucionProcessor;
            default -> throw new IllegalArgumentException(
                    "Tipo de proceso no soportado: " + tipo);
        };

    }

}
