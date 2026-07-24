package libros_service.controller;

import jakarta.validation.Valid;
import libros_service.dto.request.ActualizarEjemplarRequest;
import libros_service.dto.request.CrearEjemplarRequest;
import libros_service.dto.request.DisponibilidadRequest;
import libros_service.dto.response.ApiResponse;
import libros_service.dto.response.EjemplarResponse;
import libros_service.service.EjemplarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class EjemplarController {

    private final EjemplarService service;

    @PostMapping
    public ResponseEntity<ApiResponse<EjemplarResponse>> registrar(@Valid @RequestBody CrearEjemplarRequest request) {

        EjemplarResponse response = service.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<EjemplarResponse>builder()
                                .codigo(HttpStatus.CREATED.value())
                                .mensaje("Ejemplar registrado correctamente.")
                                .data(response)
                                .build()
                );

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EjemplarResponse>>> listar() {

        List<EjemplarResponse> response = service.listar();

        return ResponseEntity.ok(
                ApiResponse.<List<EjemplarResponse>>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Listado de ejemplares.")
                        .data(response)
                        .build());

    }

    @GetMapping("/{codigoEjemplar}")
    public ResponseEntity<ApiResponse<EjemplarResponse>> buscar(@PathVariable String codigoEjemplar) {

        EjemplarResponse response = service.buscarPorCodigo(codigoEjemplar);

        return ResponseEntity.ok(
                ApiResponse.<EjemplarResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Ejemplar encontrado.")
                        .data(response)
                        .build());

    }

    @PutMapping("/{codigoEjemplar}")
    public ResponseEntity<ApiResponse<EjemplarResponse>> actualizar(@PathVariable String codigoEjemplar,
                                                                    @Valid @RequestBody ActualizarEjemplarRequest request) {

        EjemplarResponse response =  service.actualizar(codigoEjemplar, request);

        return ResponseEntity.ok(
                ApiResponse.<EjemplarResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Ejemplar actualizado correctamente.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/{codigoEjemplar}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable String codigoEjemplar) {

        service.eliminar(codigoEjemplar);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Ejemplar eliminado correctamente.")
                        .data(null)
                        .build());

    }

    @PatchMapping("/{codigoEjemplar}/disponibilidad")
    public ResponseEntity<ApiResponse<EjemplarResponse>>actualizarDisponibilidad(@PathVariable String codigoEjemplar,
                                                                                 @Valid @RequestBody DisponibilidadRequest request) {

        EjemplarResponse response = service.actualizarDisponibilidad(codigoEjemplar,request.getDisponible());

        return ResponseEntity.ok(
                ApiResponse.<EjemplarResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Disponibilidad actualizada correctamente.")
                        .data(response)
                        .build());

    }


}