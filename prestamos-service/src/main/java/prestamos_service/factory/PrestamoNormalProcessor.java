package prestamos_service.factory;

import org.springframework.stereotype.Component;

import prestamos_service.entity.Prestamo;

@Component
public class PrestamoNormalProcessor implements PrestamoProcessor {

    @Override
    public Prestamo procesar(Prestamo prestamo) {

        prestamo.setEstado("REGISTRADA");
        prestamo.setMotivoRechazo(null);

        return prestamo;

    }

}