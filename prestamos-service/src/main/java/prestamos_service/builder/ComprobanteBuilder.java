package prestamos_service.builder;

import org.springframework.stereotype.Component;
import prestamos_service.dto.response.ComprobantePrestamoResponse;
import prestamos_service.dto.response.PrestamoResponse;

@Component
public class ComprobanteBuilder {

    public ComprobantePrestamoResponse build(PrestamoResponse response){

        String mensaje;

        switch (response.getEstado()){

            case "REGISTRADA":
                mensaje = "Préstamo registrado correctamente.";
                break;

            case "RECHAZADA":
                mensaje = response.getMotivoRechazo();
                break;

            case "DEVUELTO":
                mensaje = "Préstamo devuelto correctamente.";
                break;

            default:
                mensaje = "Estado desconocido.";
        }

        return ComprobantePrestamoResponse.builder()

                .codigoPrestamo(response.getCodigoPrestamo())

                .codigoEjemplar(response.getCodigoEjemplar())

                .codigoSocio(response.getCodigoSocio())

                .fechaPrestamo(response.getFechaPrestamo())

                .fechaDevolucionEsperada(
                        response.getFechaDevolucionEsperada())

                .estado(response.getEstado())

                .mensaje(mensaje)

                .build();

    }

}
