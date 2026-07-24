package prestamos_service.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import prestamos_service.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {


    Optional<Prestamo> findByCodigoPrestamo(String codigoPrestamo);

    boolean existsByCodigoPrestamo(String codigoPrestamo);

}