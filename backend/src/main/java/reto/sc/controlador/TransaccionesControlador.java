package reto.sc.controlador;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reto.sc.dto.TransaccionCompletaDTO;
import reto.sc.dto.TransaccionRespuestaDTO;
import reto.sc.excepcion.RecursoNoEncontradoExcepcion;
import reto.sc.modelo.Transacciones;
import reto.sc.servicios.transacciones.TransaccionesServicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sc-app")
@CrossOrigin(value = "http://localhost:3000")
public class TransaccionesControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(TransaccionesControlador.class);

    @Autowired
    private TransaccionesServicios servicio;


    @Operation(
            summary = "Agregar una transacción básica",
            description = "Registra una transacción simple (sin validación de partidas ni comprobación de balance)."
    )
    @PostMapping("/transacciones")
    public Transacciones agregar(@RequestBody Transacciones transaccion) {
        logger.info("Transacción a agregar: " + transaccion);
        return servicio.guardarTransaccion(transaccion);
    }

    @Operation(
            summary = "Buscar transacción por ID",
            description = "Obtiene una transacción por su identificador. Lanza excepción si no se encuentra."
    )
    @GetMapping("/transacciones/{id}")
    public ResponseEntity<Transacciones> obtenerPorId(@PathVariable Long id) {
        Transacciones transaccion = servicio.buscarTransaccion(id);
        if (transaccion == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        return ResponseEntity.ok(transaccion);
    }

    @Operation(
            summary = "Actualizar una transacción",
            description = "Actualiza los campos de una transacción existente usando su ID."
    )
    @PutMapping("/transacciones/{id}")
    public ResponseEntity<Transacciones> actualizarTransaccion(@PathVariable Long id, @RequestBody Transacciones recibida) {
        Transacciones transaccion = servicio.buscarTransaccion(id);
        if (transaccion == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        transaccion.setDescripcion(recibida.getDescripcion());
        transaccion.setFecha(recibida.getFecha());
        transaccion.setTerceros(recibida.getTerceros());

        servicio.guardarTransaccion(transaccion);

        return ResponseEntity.ok(transaccion);
    }

    @Operation(
            summary = "Eliminar una transacción",
            description = "Elimina una transacción de la base de datos por su ID."
    )
    @DeleteMapping("/transacciones/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarTransaccion(@PathVariable Long id) {
        Transacciones transaccion = servicio.buscarTransaccion(id);
        if (transaccion == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);

        servicio.eliminarTransaccion(transaccion);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Registrar una transacción completa",
            description = "Registra una transacción contable completa con sus respectivas partidas (débito y crédito). Valida que las partidas estén balanceadas."
    )
    @PostMapping("/transacciones-completas")
    public ResponseEntity<Transacciones> crearTransaccionCompleta(@RequestBody TransaccionCompletaDTO dto) {
        Transacciones transaccion = servicio.registrarTransaccionCompleta(dto);
        return ResponseEntity.ok(transaccion);
    }

    @Operation(
            summary = "Endpoint de prueba",
            description = "Verifica que el backend está respondiendo correctamente. Útil para pruebas rápidas."
    )
    @PostMapping("/prueba-endpoint")
    public ResponseEntity<String> prueba() {
        return ResponseEntity.ok("¡Este endpoint está funcionando!");
    }
    @Operation(
            summary = "Buscar transacciones por rango de fechas y tercero",
            description = "Permite filtrar transacciones entre fechas y por ID de tercero (opcional)."
    )
    @GetMapping("/transacciones/filtrar")
    public ResponseEntity<List<TransaccionRespuestaDTO>> filtrarTransacciones(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            @RequestParam(required = false) Long terceroId) {

        List<TransaccionRespuestaDTO> resultado = servicio.filtrarTransacciones(desde, hasta, terceroId)
                .stream()
                .map(servicio::mapearADTO)
                .toList();

        return ResponseEntity.ok(resultado);
    }


}
