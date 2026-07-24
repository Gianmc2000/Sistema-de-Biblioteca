package prestamos_service.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prestamos_service.client.LibrosClient;
import prestamos_service.dto.request.RegistrarDevolucionRequest;
import prestamos_service.dto.response.PrestamoResponse;
import prestamos_service.entity.Prestamo;
import prestamos_service.factory.PrestamoFactory;
import prestamos_service.factory.PrestamoProcessor;
import prestamos_service.mapper.PrestamoMapper;
import prestamos_service.respository.PrestamoRepository;


@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {


    @Mock
    private PrestamoRepository repository;

    @Mock
    private PrestamoFactory prestamoFactory;

    @Mock
    private PrestamoProcessor processor;

    @Mock
    private LibrosClient librosClient;

    @Mock
    private PrestamoMapper mapper;


    @InjectMocks
    private PrestamoServiceImpl service;



    @Test
    void registrarDevolucion_cuandoPrestamoExiste_retornaRespuestaCorrecta() {


        // Arrange
        RegistrarDevolucionRequest request =
                new RegistrarDevolucionRequest();

        request.setCodigoPrestamo("PRE001");


        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoPrestamo("PRE001");
        prestamo.setCodigoEjemplar("LIB001");
        prestamo.setEstado("PRESTADO");


        PrestamoResponse response =
                new PrestamoResponse();

        response.setCodigoPrestamo("PRE001");


        when(repository.findByCodigoPrestamo("PRE001"))
                .thenReturn(Optional.of(prestamo));


        when(prestamoFactory.obtenerProcesador("DEVOLUCION"))
                .thenReturn(processor);


        when(mapper.toResponse(prestamo))
                .thenReturn(response);



        // Act
        PrestamoResponse resultado =
                service.registrarDevolucion(request);



        // Assert

        assertNotNull(resultado);
        assertEquals("PRE001",
                resultado.getCodigoPrestamo());


        verify(processor)
                .procesar(prestamo);


        verify(repository)
                .save(prestamo);


        verify(librosClient)
                .actualizarDisponibilidad(
                        "LIB001",
                        true
                );


        assertNotNull(prestamo.getFechaDevolucionReal());
    }



    @Test
    void registrarDevolucion_cuandoPrestamoNoExiste_lanzaExcepcion() {


        // Arrange
        RegistrarDevolucionRequest request =
                new RegistrarDevolucionRequest();

        request.setCodigoPrestamo("PRE999");


        when(repository.findByCodigoPrestamo("PRE999"))
                .thenReturn(Optional.empty());



        // Act + Assert

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.registrarDevolucion(request)
                );


        assertEquals(
                "Préstamo no encontrado.",
                exception.getMessage()
        );


        verify(repository, never())
                .save(any());

        verifyNoInteractions(librosClient);
    }



    @Test
    void registrarDevolucion_cuandoPrestamoYaDevuelto_lanzaExcepcion() {


        // Arrange

        RegistrarDevolucionRequest request =
                new RegistrarDevolucionRequest();

        request.setCodigoPrestamo("PRE001");


        Prestamo prestamo = new Prestamo();

        prestamo.setCodigoPrestamo("PRE001");
        prestamo.setEstado("DEVUELTO");


        when(repository.findByCodigoPrestamo("PRE001"))
                .thenReturn(Optional.of(prestamo));



        // Act + Assert

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.registrarDevolucion(request)
                );



        assertEquals(
                "El préstamo ya fue devuelto.",
                exception.getMessage()
        );


        verifyNoInteractions(prestamoFactory);
        verify(repository, never())
                .save(any());

    }



    @Test
    void registrarDevolucion_debeSolicitarProcesadorDevolucion() {


        // Arrange

        RegistrarDevolucionRequest request =
                new RegistrarDevolucionRequest();

        request.setCodigoPrestamo("PRE001");


        Prestamo prestamo = new Prestamo();

        prestamo.setCodigoPrestamo("PRE001");
        prestamo.setCodigoEjemplar("LIB001");
        prestamo.setEstado("PRESTADO");


        when(repository.findByCodigoPrestamo("PRE001"))
                .thenReturn(Optional.of(prestamo));


        when(prestamoFactory.obtenerProcesador("DEVOLUCION"))
                .thenReturn(processor);


        when(mapper.toResponse(prestamo))
                .thenReturn(new PrestamoResponse());



        // Act

        service.registrarDevolucion(request);



        // Assert

        verify(prestamoFactory)
                .obtenerProcesador("DEVOLUCION");


        verify(processor)
                .procesar(prestamo);
    }

}