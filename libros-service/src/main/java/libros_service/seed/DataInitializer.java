package libros_service.seed;

import libros_service.entity.Ejemplar;
import libros_service.entity.Socio;
import libros_service.repository.EjemplarRepository;
import libros_service.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EjemplarRepository ejemplarRepository;
    private final SocioRepository socioRepository;

    @Override
    public void run(String... args) {

        if (ejemplarRepository.count() == 0) {

            ejemplarRepository.save(
                    Ejemplar.builder()
                            .codigoEjemplar("BIB-0001")
                            .titulo("Clean Code")
                            .autor("Robert C. Martin")
                            .isbn("9780132350884")
                            .anioPublicacion(2008)
                            .disponible(true)
                            .build());

            ejemplarRepository.save(
                    Ejemplar.builder()
                            .codigoEjemplar("BIB-0002")
                            .titulo("Effective Java")
                            .autor("Joshua Bloch")
                            .isbn("9780134685991")
                            .anioPublicacion(2018)
                            .disponible(true)
                            .build());

            ejemplarRepository.save(
                    Ejemplar.builder()
                            .codigoEjemplar("BIB-0003")
                            .titulo("Spring in Action")
                            .autor("Craig Walls")
                            .isbn("9781617297571")
                            .anioPublicacion(2022)
                            .disponible(false)
                            .build());

        }

        if (socioRepository.count() == 0) {

            socioRepository.save(
                    Socio.builder()
                            .codigoSocio("S001")
                            .nombre("Juan Pérez")
                            .email("juan@correo.com")
                            .telefono("999111222")
                            .fechaInscripcion(LocalDate.now())
                            .activo(true)
                            .build());

            socioRepository.save(
                    Socio.builder()
                            .codigoSocio("S002")
                            .nombre("María López")
                            .email("maria@correo.com")
                            .telefono("999333444")
                            .fechaInscripcion(LocalDate.now())
                            .activo(false)
                            .build());

        }
    }
}
