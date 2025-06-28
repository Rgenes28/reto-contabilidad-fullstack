package reto.sc.servicios.partidascontables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reto.sc.dto.ResumenCuentaDTO;
import reto.sc.dto.SaldoCuentaDTO;
import reto.sc.excepcion.RecursoNoEncontradoExcepcion;
import reto.sc.modelo.CuentasContables;
import reto.sc.modelo.PartidasContables;
import reto.sc.modelo.Transacciones;
import reto.sc.repositorio.CuentasContablesRepositorio;
import reto.sc.repositorio.PartidasRepositorio;
import reto.sc.repositorio.TransaccionesRepositorio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartidasContablesServicios implements IPartidasContablesServicios {

    @Autowired
    private PartidasRepositorio partidasRepositorio;

    @Autowired
    private TransaccionesRepositorio transaccionesRepositorio;

    @Autowired
    private CuentasContablesRepositorio cuentasContablesRepositorio;

    @Override
    public List<PartidasContables> listarTodos() {
        return partidasRepositorio.findAll();
    }

    @Override
    public PartidasContables buscarPartidasContables(Long id) {
        return partidasRepositorio.findById(id).orElse(null);
    }

    @Override
    public PartidasContables guardarPartidasContables(PartidasContables partida) {
        Long idTransaccion = partida.getTransaccion().getId();
        Long idCuenta = partida.getCuentasContables().getId();
        // Validar existencia de transacción
        Transacciones transaccion = transaccionesRepositorio.findById(idTransaccion)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Transacción no encontrada con id: " + idTransaccion));

        // Validar existencia de cuenta contable
        CuentasContables cuenta = cuentasContablesRepositorio.findById(idCuenta)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Cuenta contable no encontrada con id: " + idCuenta));

        // Asignar relaciones completas
        partida.setTransaccion(transaccion);
        partida.setCuentasContables(cuenta);

        return partidasRepositorio.save(partida);
    }

    @Override
    public void eliminarPartida(PartidasContables partidasContables) {
        partidasRepositorio.delete(partidasContables);

    }

    public SaldoCuentaDTO calcularSaldoPorCuenta(Long cuentaId) {
        List<PartidasContables> partidas = partidasRepositorio.findByCuentasContablesId(cuentaId);

        BigDecimal totalDebito = BigDecimal.ZERO;
        BigDecimal totalCredito = BigDecimal.ZERO;

        for (PartidasContables p : partidas) {
            if (p.getTipo().equalsIgnoreCase("debito")) {
                totalDebito = totalDebito.add(p.getValor());
            } else if (p.getTipo().equalsIgnoreCase("credito")) {
                totalCredito = totalCredito.add(p.getValor());
            }
        }

        BigDecimal saldo = totalDebito.subtract(totalCredito);

        return new SaldoCuentaDTO(cuentaId, totalDebito, totalCredito, saldo);
    }

    public List<ResumenCuentaDTO> obtenerResumenSaldosPorCuenta() {
        List<CuentasContables> cuentas = cuentasContablesRepositorio.findAll();
        List<ResumenCuentaDTO> resumen = new ArrayList<>();

        for (CuentasContables cuenta : cuentas) {
            List<PartidasContables> partidas = partidasRepositorio.findByCuentasContables(cuenta);

            BigDecimal debitos = partidas.stream()
                    .filter(p -> p.getTipo().equalsIgnoreCase("debito"))
                    .map(PartidasContables::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal creditos = partidas.stream()
                    .filter(p -> p.getTipo().equalsIgnoreCase("credito"))
                    .map(PartidasContables::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saldo = debitos.subtract(creditos);

            resumen.add(new ResumenCuentaDTO(
                    cuenta.getId(),
                    cuenta.getNombre(),
                    cuenta.getCodigo(),
                    saldo
            ));
        }

        return resumen;
    }


}
