package reto.sc.controlador;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reto.sc.excepcion.RecursoNoEncontradoExcepcion;
import reto.sc.modelo.Terceros;
import reto.sc.servicios.terceros.ITercerosServicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sc-app")
@CrossOrigin(value = "http://localhost:3000")
public class TercerosControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(TercerosControlador.class);

    @Autowired
    private ITercerosServicios tercerosServicios;

    @Operation(
            summary = "Listar todos los terceros",
            description = "Obtiene una lista con todos los registros de terceros disponibles."
    )
    @GetMapping("/terceros")
    public List<Terceros> obtenerTerceros() {
        var terceros = tercerosServicios.listarTodos();
        terceros.forEach(tercero -> logger.info(tercero.toString()));
        return terceros;
    }

    @Operation(
            summary = "Registrar un nuevo tercero",
            description = "Crea un nuevo tercero con los datos enviados en el cuerpo de la solicitud."
    )
    @PostMapping("/terceros")
    public Terceros agregarTercero(@RequestBody Terceros tercero) {
        logger.info("Tercero a agregar: " + tercero);
        return tercerosServicios.guardarTercero(tercero);
    }

    @Operation(
            summary = "Buscar tercero por ID",
            description = "Devuelve los datos de un tercero específico si existe en la base de datos."
    )
    @GetMapping("/terceros/{id}")
    public ResponseEntity<Terceros> obtenerTerceroPorId(@PathVariable Long id) {
        Terceros tercero = tercerosServicios.buscarTercero(id);
        if (tercero == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        return ResponseEntity.ok(tercero);
    }

    @Operation(
            summary = "Actualizar un tercero",
            description = "Actualiza los datos del tercero identificado por el ID proporcionado."
    )
    @PutMapping("/terceros/{id}")
    public ResponseEntity<Terceros> actualizarTercero(@PathVariable Long id, @RequestBody Terceros terceroRecibido) {
        Terceros tercero = tercerosServicios.buscarTercero(id);
        if (tercero == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        tercero.setNombre(terceroRecibido.getNombre());
        tercero.setTipoDocumento(terceroRecibido.getTipoDocumento());
        tercero.setNumeroDocumento(terceroRecibido.getNumeroDocumento());
        tercerosServicios.guardarTercero(tercero);
        return ResponseEntity.ok(tercero);
    }

    @Operation(
            summary = "Eliminar un tercero",
            description = "Elimina el tercero identificado por el ID proporcionado si existe."
    )
    @DeleteMapping("/terceros/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarTercero(@PathVariable Long id) {
        Terceros tercero = tercerosServicios.buscarTercero(id);
        if (tercero == null)
            throw new RecursoNoEncontradoExcepcion("El id recibido no existe: " + id);
        tercerosServicios.eliminarTercero(id);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}

