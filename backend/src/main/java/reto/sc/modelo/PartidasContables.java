package reto.sc.modelo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "PARTIDASCONTABLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"transaccion", "cuentasContables"})
public class PartidasContables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARTIDA")
    private Long id;

    @Column(name = "TIPO")
    private String tipo; // "debito" o "credito"

    @Column(name = "VALOR")
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "ID_TRANSACCION") // FK hacia TRANSACCIONES
    private Transacciones transaccion;

    @ManyToOne
    @JoinColumn(name = "ID_CUENTA_CONTABLE") // FK hacia CUENTASCONTABLES
    private CuentasContables cuentasContables;
}
