

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String dni;
    private LocalDate fechaNacimiento;

    private String idProspecto;

    @Column(nullable = true)
    private String idCliente; // Se asignará solo si contrata una póliza

    @PrePersist
    public void generarIdProspecto() {
        if (this.idProspecto == null || this.idProspecto.isBlank()) {
            this.idProspecto = UUID.randomUUID().toString();
        }
    }
}