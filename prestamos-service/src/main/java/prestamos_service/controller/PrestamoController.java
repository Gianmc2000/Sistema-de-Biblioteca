package prestamos_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prestamos_service.dto.request.RegistrarDevolucionRequest;
import prestamos_service.dto.request.RegistrarPrestamoRequest;
import prestamos_service.dto.response.ApiResponse;
import prestamos_service.dto.response.ComprobantePrestamoResponse;
import prestamos_service.dto.response.PrestamoResponse;
import prestamos_service.factory.MensajeNotificacionFactory;
import prestamos_service.service.PrestamoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService service;
    private final MensajeNotificacionFactory mensajeNotificacionFactory;

    @PostMapping
    public ResponseEntity<ApiResponse<ComprobantePrestamoResponse>> registrar(
            @Valid @RequestBody RegistrarPrestamoRequest request){

        ComprobantePrestamoResponse response = service.registrarPrestamo(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<ComprobantePrestamoResponse>builder()
                                .codigo(HttpStatus.CREATED.value())
                                .mensaje(mensajeNotificacionFactory.crearMensaje(response.getEstado(), response.getCodigoEjemplar(), response.getCodigoSocio()))
                                .data(response)
                                .build()
                );
    }

    @PostMapping("/{id}/devolucion") // <-- Ajustado al path del examen
    public ResponseEntity<ApiResponse<PrestamoResponse>> devolver(@PathVariable String id) {

        RegistrarDevolucionRequest request = RegistrarDevolucionRequest.builder()
                .codigoPrestamo(id)
                .build();

        PrestamoResponse response = service.registrarDevolucion(request);

        return ResponseEntity.ok(
                ApiResponse.<PrestamoResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje(mensajeNotificacionFactory.crearMensaje(response.getEstado(), response.getCodigoEjemplar(), response.getCodigoSocio()))
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PrestamoResponse>>> listar(){
        return ResponseEntity.ok(
                ApiResponse.<List<PrestamoResponse>>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Listado de préstamos.")
                        .data(service.listar())
                        .build()
        );
    }

    @GetMapping("/{codigoPrestamo}")
    public ResponseEntity<ApiResponse<PrestamoResponse>> buscar(@PathVariable String codigoPrestamo){
        return ResponseEntity.ok(
                ApiResponse.<PrestamoResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Préstamo encontrado.")
                        .data(service.buscar(codigoPrestamo))
                        .build()
        );
    }
}