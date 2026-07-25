package libros_service.controller;

import jakarta.validation.Valid;
import libros_service.dto.request.ActualizarSocioRequest;
import libros_service.dto.request.CrearSocioRequest;
import libros_service.dto.response.ApiResponse;
import libros_service.dto.response.EjemplarResponse;
import libros_service.dto.response.SocioResponse;
import libros_service.service.SocioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/socios")
@RequiredArgsConstructor
public class SocioController {

    private final SocioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<SocioResponse>> registrar(@Valid @RequestBody CrearSocioRequest request){

        SocioResponse response= service.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<SocioResponse>builder()
                                .codigo(HttpStatus.CREATED.value())
                                .mensaje("Ejemplar registrado correctamente.")
                                .data(response)
                                .build()
                );

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SocioResponse>>> listar(){

        List<SocioResponse> response= service.listar();

        return ResponseEntity.ok(
                ApiResponse.<List<SocioResponse>>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Listado de ejemplares.")
                        .data(response)
                        .build());

    }

    @GetMapping("/{codigoSocio}")
    public ResponseEntity<ApiResponse<SocioResponse>> buscar(@PathVariable String codigoSocio){

        SocioResponse response =
                service.buscarPorCodigo(codigoSocio);

        return ResponseEntity.ok(
                ApiResponse.<SocioResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Socio encontrado.")
                        .data(response)
                        .build());

    }

    @PutMapping("/{codigoSocio}")
    public ResponseEntity<ApiResponse<SocioResponse>> actualizar(@PathVariable String codigoSocio,@Valid @RequestBody ActualizarSocioRequest request){

        SocioResponse response = service.actualizar(codigoSocio,request);
        return ResponseEntity.ok(
                ApiResponse.<SocioResponse>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Ejemplar actualizado correctamente.")
                        .data(response)
                        .build());

    }

    @DeleteMapping("/{codigoSocio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable String codigoSocio){

        service.eliminar(codigoSocio);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .codigo(HttpStatus.OK.value())
                        .mensaje("Socio Eliminado")
                        .data(null)
                        .build());

    }

}
