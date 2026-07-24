package prestamos_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK Autogenerada según documento

    private String codigoPrestamo; // Campo para respuesta en DTOs

    private String codigoEjemplar;

    private String codigoSocio;

    private LocalDateTime fechaPrestamo; // <-- LocalDateTime según especificación[cite: 1]

    private LocalDate fechaDevolucionEsperada; // <-- LocalDate según especificación[cite: 1]

    private LocalDateTime fechaDevolucionReal; // <-- LocalDateTime (null hasta devolución)[cite: 1]

    private String estado; // REGISTRADA / RECHAZADA / DEVUELTO[cite: 1]

    private String motivoRechazo; // null si no fue rechazada[cite: 1]

    private String observaciones;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaCreacion; // Auditoría automática[cite: 1]

    @UpdateTimestamp
    private LocalDateTime fechaActualizacion; // Auditoría automática[cite: 1]
}