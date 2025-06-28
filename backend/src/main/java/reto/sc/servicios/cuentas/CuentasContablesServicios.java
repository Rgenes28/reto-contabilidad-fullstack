package reto.sc.servicios.cuentas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reto.sc.modelo.CuentasContables;
import reto.sc.repositorio.CuentasContablesRepositorio;
import reto.sc.repositorio.PartidasRepositorio;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentasContablesServicios implements ICuentasContablesServicios {

    @Autowired
    private CuentasContablesRepositorio cuentasRepo;

    @Autowired
    private PartidasRepositorio partidasContablesRepositorio;

    @Override
    public List<CuentasContables> listarTodos() {
        return cuentasRepo.findAll();
    }

    @Override
    public CuentasContables buscarCuenta(Long id) {
        return cuentasRepo.findById(id).orElse(null);
    }

    @Override
    public CuentasContables guardarCuenta(CuentasContables cuenta) {
        return cuentasRepo.save(cuenta);
    }

    @Override
    public void eliminarCuenta(CuentasContables cuenta) {
        cuentasRepo.delete(cuenta);
    }

    public BigDecimal calcularSaldoCuenta(Long cuentaId) {
        BigDecimal debitos = partidasContablesRepositorio.sumValorByCuentaAndTipo(cuentaId, "debito");
        BigDecimal creditos = partidasContablesRepositorio.sumValorByCuentaAndTipo(cuentaId, "credito");

        if (debitos == null) debitos = BigDecimal.ZERO;
        if (creditos == null) creditos = BigDecimal.ZERO;

        return debitos.subtract(creditos);
    }


}
