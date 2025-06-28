package reto.sc.controlador;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reto.sc.excepcion.RecursoNoEncontradoExcepcion;
import reto.sc.modelo.CuentasContables;
import reto.sc.repositorio.CuentasContablesRepositorio;
import reto.sc.servicios.cuentas.CuentasContablesServicios;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sc-app")
@CrossOrigin(value = "http://localhost:3000")
public class CuentasContablesControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(CuentasContablesControlador.class);

    @Autowired
    private CuentasContablesServicios servicio;

    @Autowired
    private CuentasContablesRepositorio cuentasContablesRepositorio;


    @Operation(summary = "Obtener todas las cuentas contables")
    @GetMapping("/cuentas")
    public List<CuentasContables> obtenerCuentas() {
        var cuentas = servicio.listarTodos();
        cuentas.forEach(cuenta -> logger.info(cuenta.toString()));
        return cuentas;
    }

    @Operation(summary = "Agregar una nueva cuenta contable")
    @PostMapping("/cuentas")
    public CuentasContables agregar(@RequestBody CuentasContables cuenta) {
        logger.info("Cuenta a agregar: " + cuenta);
        return servicio.guardarCuenta(cuenta);
    }

    @Operation(summary = "Obtener una cuenta contable por ID")
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<CuentasContables> obtenerPorId(@PathVariable Long id) {
        CuentasContables cuenta = servicio.buscarCuenta(id);
        if (cuenta == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Actualizar una cuenta contable")
    @PutMapping("/cuentas/{id}")
    public ResponseEntity<CuentasContables> actualizarCuenta(@PathVariable Long id, @RequestBody CuentasContables recibida) {
        CuentasContables cuenta = servicio.buscarCuenta(id);
        if (cuenta == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        cuenta.setNombre(recibida.getNombre());
        cuenta.setCodigo(recibida.getCodigo());
        cuenta.setTipo(recibida.getTipo());
        cuenta.setPermiteSaldoNegativo(recibida.getPermiteSaldoNegativo());
        cuenta.setActivo(recibida.getActivo());

        servicio.guardarCuenta(cuenta);
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Eliminar una cuenta contable")
    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCuenta(@PathVariable Long id) {
        CuentasContables cuenta = servicio.buscarCuenta(id);
        if (cuenta == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        servicio.eliminarCuenta(cuenta);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    // -------------------- NUEVAS FUNCIONALIDADES --------------------

    @Operation(summary = "Obtener solo las cuentas activas")
    @GetMapping("/cuentas/activas")
    public List<CuentasContables> obtenerCuentasActivas() {
        return cuentasContablesRepositorio.findByActivoTrue();
    }

    @Operation(
            summary = "Cambiar el estado de una cuenta (activar/inactivar)",
            description = "Permite activar o inactivar una cuenta contable enviando en el cuerpo { \"activo\": true/false }"
    )
    @PutMapping("/cuentas/{id}/estado")
    public ResponseEntity<?> cambiarEstadoCuenta(@PathVariable Long id, @RequestBody Map<String, Boolean> estado) {
        CuentasContables cuenta = cuentasContablesRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        cuenta.setActivo(estado.get("activo"));
        cuentasContablesRepositorio.save(cuenta);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/cuentas/{id}/saldo")
    public ResponseEntity<BigDecimal> obtenerSaldoPorCuenta(@PathVariable Long id) {
        BigDecimal saldo = servicio.calcularSaldoCuenta(id);
        return ResponseEntity.ok(saldo);
    }

}

