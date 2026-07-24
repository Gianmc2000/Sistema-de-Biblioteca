package libros_service.mapper;

import org.springframework.stereotype.Component;

import libros_service.dto.request.ActualizarEjemplarRequest;
import libros_service.dto.request.CrearEjemplarRequest;
import libros_service.dto.response.EjemplarResponse;
import libros_service.entity.Ejemplar;

@Component
public class EjemplarMapper {

    public Ejemplar toEntity(CrearEjemplarRequest dto) {

        return Ejemplar.builder()
                .codigoEjemplar(dto.getCodigoEjemplar())
                .titulo(dto.getTitulo())
                .autor(dto.getAutor())
                .isbn(dto.getIsbn())
                .anioPublicacion(dto.getAnoPublicacion())
                .disponible(dto.getDisponible())
                .build();

    }

 
    public EjemplarResponse toResponse(Ejemplar entity) {

        return EjemplarResponse.builder()
                .codigoEjemplar(entity.getCodigoEjemplar())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .isbn(entity.getIsbn())
                .anioPublicacion(entity.getAnioPublicacion())
                .disponible(entity.getDisponible())
                .build();

    }


    public void actualizarEntidad(Ejemplar entity,
                                  ActualizarEjemplarRequest dto) {

        entity.setTitulo(dto.getTitulo());
        entity.setAutor(dto.getAutor());
        entity.setDisponible(dto.getDisponible());

    }


}
