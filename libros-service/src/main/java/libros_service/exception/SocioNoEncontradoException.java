package libros_service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SocioNoEncontradoException extends RuntimeException{

    public SocioNoEncontradoException(String codigo){

        super("No existe el socio con código : " + codigo);

    }

}
