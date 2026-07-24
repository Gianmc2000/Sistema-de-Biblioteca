package prestamos_service.factory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import prestamos_service.entity.Prestamo;

@Component
public class DevolucionProcessor implements PrestamoProcessor {

    @Override
    public Prestamo procesar(Prestamo prestamo) {

        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucionReal(LocalDateTime.now());

        return prestamo;

    }

}