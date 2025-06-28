package reto.sc.controlador;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reto.sc.dto.ResumenCuentaDTO;
import reto.sc.dto.SaldoCuentaDTO;
import reto.sc.excepcion.RecursoNoEncontradoExcepcion;
import reto.sc.modelo.PartidasContables;
import reto.sc.servicios.partidascontables.PartidasContablesServicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sc-app")
@CrossOrigin(value = "http://localhost:3000")
public class PartidasContablesControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(PartidasContablesControlador.class);

    @Autowired
    private PartidasContablesServicios servicio;

    @Operation(
            summary = "Listar todas las partidas contables",
            description = "Obtiene la lista completa de partidas contables registradas en el sistema."
    )
    @GetMapping("/partidas")
    public ResponseEntity<?> obtenerPartidas() {
        try {
            var partidas = servicio.listarTodos();
            partidas.forEach(partida -> logger.info(partida.toString()));
            return ResponseEntity.ok(partidas);
        } catch (Exception e) {
            logger.error("Error al obtener partidas", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Registrar una nueva partida contable",
            description = "Registra una nueva partida contable con tipo, valor, transacción y cuenta contable asociada."
    )
    @PostMapping("/partidas")
    public PartidasContables agregar(@RequestBody PartidasContables partida) {
        logger.info("Partida a agregar: " + partida);
        return servicio.guardarPartidasContables(partida);
    }

    @Operation(
            summary = "Obtener una partida contable por ID",
            description = "Busca y devuelve una partida contable específica a partir del ID proporcionado."
    )
    @GetMapping("/partidas/{id}")
    public ResponseEntity<PartidasContables> obtenerPorId(@PathVariable Long id) {
        PartidasContables partida = servicio.buscarPartidasContables(id);
        if (partida == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        return ResponseEntity.ok(partida);
    }

    @Operation(
            summary = "Actualizar una partida contable",
            description = "Actualiza los campos de una partida contable existente identificada por su ID."
    )
    @PutMapping("/partidas/{id}")
    public ResponseEntity<PartidasContables> actualizarPartida(@PathVariable Long id, @RequestBody PartidasContables recibida) {
        PartidasContables partida = servicio.buscarPartidasContables(id);
        if (partida == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        partida.setTipo(recibida.getTipo());
        partida.setValor(recibida.getValor());
        partida.setCuentasContables(recibida.getCuentasContables());
        partida.setTransaccion(recibida.getTransaccion());

        servicio.guardarPartidasContables(partida);

        return ResponseEntity.ok(partida);
    }

    @Operation(
            summary = "Eliminar una partida contable",
            description = "Elimina del sistema la partida contable asociada al ID proporcionado."
    )
    @DeleteMapping("/partidas/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarPartida(@PathVariable Long id) {
        PartidasContables partida = servicio.buscarPartidasContables(id);
        if (partida == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        servicio.eliminarPartida(partida);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Obtener el saldo de una cuenta contable",
            description = "Calcula el saldo total (débito - crédito) de una cuenta contable específica según su ID."
    )
    @GetMapping("/partidas/saldo/{cuentaId}")
    public ResponseEntity<SaldoCuentaDTO> obtenerSaldo(@PathVariable Long cuentaId) {
        SaldoCuentaDTO saldo = servicio.calcularSaldoPorCuenta(cuentaId);
        return ResponseEntity.ok(saldo);
    }

    @Operation(
            summary = "Obtener resumen de saldos por cuenta",
            description = "Devuelve un listado con los saldos totales agrupados por cuenta contable."
    )
    @GetMapping("/resumen-saldos")
    public ResponseEntity<List<ResumenCuentaDTO>> obtenerResumenSaldos() {
        return ResponseEntity.ok(servicio.obtenerResumenSaldosPorCuenta());
    }

}

