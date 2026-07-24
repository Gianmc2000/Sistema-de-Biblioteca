package libros_service.controller;

import jakarta.validation.Valid;
import libros_service.dto.request.ActualizarSocioRequest;
import libros_service.dto.request.CrearSocioRequest;
import libros_service.dto.response.ApiResponse;
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
    public SocioResponse registrar(@Valid @RequestBody CrearSocioRequest request){

        return service.registrar(request);

    }

    @GetMapping
    public List<SocioResponse> listar(){

        return service.listar();

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
    public SocioResponse actualizar(@PathVariable String codigoSocio,@Valid @RequestBody ActualizarSocioRequest request){

        return service.actualizar(codigoSocio,request);

    }

    @DeleteMapping("/{codigoSocio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String codigoSocio){

        service.eliminar(codigoSocio);

    }

}
