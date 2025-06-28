package reto.sc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import reto.sc.modelo.PartidasContables;
import reto.sc.modelo.Transacciones;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransaccionServiceTest {

    private boolean validarBalance(Transacciones transaccion) {
        BigDecimal totalDebito = transaccion.getPartidas().stream()
            .filter(p -> "debito".equalsIgnoreCase(p.getTipo()))
            .map(PartidasContables::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCredito = transaccion.getPartidas().stream()
            .filter(p -> "credito".equalsIgnoreCase(p.getTipo()))
            .map(PartidasContables::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalDebito.compareTo(totalCredito) == 0;
    }

    @Test
    void debeBalancearTransaccionCorrectamente() {
        Transacciones transaccion = new Transacciones();
        transaccion.setDescripcion("Prueba");
        transaccion.setFecha(LocalDate.now());

        PartidasContables debito = new PartidasContables();
        debito.setTipo("debito");
        debito.setValor(new BigDecimal("3000000"));

        PartidasContables credito = new PartidasContables();
        credito.setTipo("credito");
        credito.setValor(new BigDecimal("3000000"));

        transaccion.setPartidas(List.of(debito, credito));

        boolean balanceada = validarBalance(transaccion);
        assertTrue(balanceada, "La transacción debería estar balanceada");
    }
}
