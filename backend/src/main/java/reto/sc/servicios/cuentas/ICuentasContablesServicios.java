package reto.sc.servicios.cuentas;

import reto.sc.modelo.CuentasContables;

import java.util.List;

public interface ICuentasContablesServicios {

    public List<CuentasContables> listarTodos();
    public CuentasContables buscarCuenta(Long id);
    public CuentasContables guardarCuenta(CuentasContables cuenta);
    public void eliminarCuenta(CuentasContables cuenta);
}
