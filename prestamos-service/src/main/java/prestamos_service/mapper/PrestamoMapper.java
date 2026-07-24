package prestamos_service.mapper;

import org.springframework.stereotype.Component;
import prestamos_service.dto.response.PrestamoResponse;
import prestamos_service.entity.Prestamo;

@Component
public class PrestamoMapper {

    public PrestamoResponse toResponse(Prestamo entity) {
        if (entity == null) {
            return null;
        }

        return PrestamoResponse.builder()
                .codigoPrestamo(entity.getCodigoPrestamo() != null ? entity.getCodigoPrestamo() : String.valueOf(entity.getId()))
                .codigoEjemplar(entity.getCodigoEjemplar())
                .codigoSocio(entity.getCodigoSocio())
                .fechaPrestamo(entity.getFechaPrestamo())
                .fechaDevolucionEsperada(entity.getFechaDevolucionEsperada())
                .fechaDevolucionReal(entity.getFechaDevolucionReal())
                .estado(entity.getEstado())
                .motivoRechazo(entity.getMotivoRechazo())
                .build();
    }
}