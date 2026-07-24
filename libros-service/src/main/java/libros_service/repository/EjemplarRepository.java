package libros_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import libros_service.entity.Ejemplar;

public interface EjemplarRepository extends JpaRepository<Ejemplar, String> {

}
