package libros_service.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import libros_service.dto.request.ActualizarSocioRequest;
import libros_service.dto.request.CrearSocioRequest;
import libros_service.dto.response.SocioResponse;
import libros_service.entity.Socio;


@Component
public class SocioMapper {

    public Socio toEntity(CrearSocioRequest dto){

        return Socio.builder()
                .codigoSocio(dto.getCodigoSocio())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .fechaInscripcion(LocalDate.now())
                .activo(true)
                .build();

    }

    public SocioResponse toResponse(Socio entity){

        return SocioResponse.builder()
                .codigoSocio(entity.getCodigoSocio())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .telefono(entity.getTelefono())
                .fechaInscripcion(entity.getFechaInscripcion())
                .activo(entity.getActivo())
                .build();

    }

    public void actualizarEntidad(Socio entity,
                                  ActualizarSocioRequest dto){

        entity.setNombre(dto.getNombre());
        entity.setEmail(dto.getEmail());
        entity.setActivo(dto.getActivo());

    }

}