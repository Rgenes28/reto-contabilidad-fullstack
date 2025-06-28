package reto.sc.modelo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "TRANSACCIONES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"partidas", "terceros"})
public class Transacciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACCION")
    private Long id;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TERCERO") // Clave foránea hacia la tabla TERCEROS
    private Terceros terceros;

    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartidasContables> partidas;
}
