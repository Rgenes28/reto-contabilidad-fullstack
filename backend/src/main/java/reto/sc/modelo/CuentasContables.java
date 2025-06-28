package reto.sc.modelo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "CUENTASCONTABLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CuentasContables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUENTA")
    private Long id;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "PERMITE_SALDO_NEGATIVO")
    private Boolean permiteSaldoNegativo;

    @Column(name = "ACTIVO")
    private Boolean activo = true;
}


