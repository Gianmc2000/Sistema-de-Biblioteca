package prestamos_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import prestamos_service.dto.response.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> runtime(RuntimeException ex){

        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.<Void>builder()
                                .codigo(HttpStatus.BAD_REQUEST.value())
                                .mensaje(ex.getMessage())
                                .data(null)
                                .build()
                );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> validation(
            MethodArgumentNotValidException ex){

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.<Void>builder()
                                .codigo(400)
                                .mensaje(mensaje)
                                .data(null)
                                .build()
                );

    }

}