package libros_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import libros_service.entity.Socio;

public interface SocioRepository extends JpaRepository<Socio, String> {

}
