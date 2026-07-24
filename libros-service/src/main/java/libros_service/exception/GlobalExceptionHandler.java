package libros_service.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import libros_service.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EjemplarNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> ejemplarNoEncontrado(
            EjemplarNoEncontradoException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.<Void>builder()
                                .codigo(HttpStatus.NOT_FOUND.value())
                                .mensaje(ex.getMessage())
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(SocioNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> socioNoEncontrado(
            SocioNoEncontradoException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.<Void>builder()
                                .codigo(HttpStatus.NOT_FOUND.value())
                                .mensaje(ex.getMessage())
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> validacionRequest(
            MethodArgumentNotValidException ex){


        String mensajes = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.<Void>builder()
                                .codigo(HttpStatus.BAD_REQUEST.value())
                                .mensaje(mensajes)
                                .data(null)
                                .build()
                );
    }
}
