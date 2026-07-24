package libros_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EjemplarNoEncontradoException extends RuntimeException{

    public EjemplarNoEncontradoException(String codigo){

        super("No existe el ejemplar con código : " + codigo);

    }

}
