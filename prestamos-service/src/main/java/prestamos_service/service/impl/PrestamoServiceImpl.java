package prestamos_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import prestamos_service.builder.ComprobanteBuilder;
import prestamos_service.client.LibrosClient;
import prestamos_service.client.NotificacionesClient;
import prestamos_service.dto.request.NotificacionRequest;
import prestamos_service.dto.request.RegistrarDevolucionRequest;
import prestamos_service.dto.request.RegistrarPrestamoRequest;
import prestamos_service.dto.response.ApiResponse;
import prestamos_service.dto.response.ComprobantePrestamoResponse;
import prestamos_service.dto.response.EjemplarResponse;
import prestamos_service.dto.response.PrestamoResponse;
import prestamos_service.dto.response.SocioResponse;
import prestamos_service.entity.Prestamo;
import prestamos_service.factory.PrestamoFactory;
import prestamos_service.factory.PrestamoProcessor;
import prestamos_service.mapper.PrestamoMapper;
import prestamos_service.respository.PrestamoRepository;
import prestamos_service.service.PrestamoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository repository;
    private final PrestamoMapper mapper;
    private final LibrosClient librosClient;
    private final NotificacionesClient notificacionesClient;
    private final PrestamoFactory prestamoFactory;
    private final ComprobanteBuilder comprobanteBuilder;

    @Override
    public ComprobantePrestamoResponse registrarPrestamo(RegistrarPrestamoRequest request) {

        Prestamo prestamo = Prestamo.builder()
                .codigoPrestamo(request.getCodigoPrestamo())
                .codigoEjemplar(request.getCodigoEjemplar())
                .codigoSocio(request.getCodigoSocio())
                .fechaPrestamo(LocalDateTime.now()) 
                .fechaDevolucionEsperada(LocalDate.now().plusDays(7))
                .build();

        try {
            ApiResponse<SocioResponse> socioResponse = librosClient.obtenerSocio(request.getCodigoSocio());
            SocioResponse socio = (socioResponse != null) ? socioResponse.getData() : null;
            if(repository.existsByCodigoPrestamo(request.getCodigoPrestamo())) {
                prestamo.setEstado("RECHAZADA");
                prestamo.setMotivoRechazo("Código de préstamo ya registrado");
                return comprobanteBuilder.build(mapper.toResponse(prestamo));
            }

            if (socio == null || !socio.getActivo()) {
                prestamo.setEstado("RECHAZADA");
                if (socio == null) {
                    prestamo.setMotivoRechazo("Socio no encontrado");
                } else {
                    prestamo.setMotivoRechazo("Socio inactivo");
                }
                repository.save(prestamo);
                return comprobanteBuilder.build(mapper.toResponse(prestamo));
            }

            ApiResponse<EjemplarResponse> ejemplarResponse = librosClient.obtenerEjemplar(request.getCodigoEjemplar());
            EjemplarResponse ejemplar = (ejemplarResponse != null) ? ejemplarResponse.getData() : null;

            if (ejemplar == null || ejemplar.getDisponible() == null || !ejemplar.getDisponible()) {
                prestamo.setEstado("RECHAZADA");
                prestamo.setMotivoRechazo("Ejemplar no disponible");
                repository.save(prestamo);
                return comprobanteBuilder.build(mapper.toResponse(prestamo));
            }

            PrestamoProcessor processor = prestamoFactory.obtenerProcesador("PRESTAMO");
            processor.procesar(prestamo);

            repository.save(prestamo);

            librosClient.actualizarDisponibilidad(prestamo.getCodigoEjemplar(), false);

            notificacionesClient.enviar(NotificacionRequest.builder()
                            .codigoPrestamo(prestamo.getCodigoPrestamo())
                            .codigoSocio(socio.getCodigoSocio())
                            .email(socio.getEmail())
                            .asunto("Préstamo registrado")
                            .mensaje("Su préstamo fue registrado correctamente.")
                            .build());

            return comprobanteBuilder.build(mapper.toResponse(prestamo));

        } catch (HttpClientErrorException.NotFound ex) {
            prestamo.setEstado("RECHAZADA");
            prestamo.setMotivoRechazo("Socio o ejemplar no encontrado");
            repository.save(prestamo);
            return comprobanteBuilder.build(mapper.toResponse(prestamo));

        } catch (Exception ex) {
            prestamo.setEstado("RECHAZADA");
            prestamo.setMotivoRechazo("Error de comunicación con servicios externos");
            repository.save(prestamo);
            return comprobanteBuilder.build(mapper.toResponse(prestamo));
        }
    }

    @Override
    public PrestamoResponse registrarDevolucion(RegistrarDevolucionRequest request) {

        Prestamo prestamo = repository.findByCodigoPrestamo(request.getCodigoPrestamo())
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado."));


        if ("DEVUELTO".equals(prestamo.getEstado())) {
            throw new RuntimeException("El préstamo ya fue devuelto.");
        }

        PrestamoProcessor processor = prestamoFactory.obtenerProcesador("DEVOLUCION");
        processor.procesar(prestamo);

        prestamo.setFechaDevolucionReal(LocalDateTime.now());

        repository.save(prestamo);

        librosClient.actualizarDisponibilidad(prestamo.getCodigoEjemplar(), true);

        return mapper.toResponse(prestamo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamoResponse buscar(String codigoPrestamo) {
        return mapper.toResponse(repository.findByCodigoPrestamo(codigoPrestamo)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado.")));
    }
}