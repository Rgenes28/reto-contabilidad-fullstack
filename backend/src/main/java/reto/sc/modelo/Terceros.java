package reto.sc.modelo;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "TERCEROS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Terceros {
    @Id @GeneratedValue
    @Column(name = "ID_TERCERO")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;


    @OneToMany(mappedBy = "terceros")
    private List<Transacciones> transacciones;
}
