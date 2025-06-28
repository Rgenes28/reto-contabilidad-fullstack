package reto.sc.servicios.transacciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reto.sc.dto.TransaccionCompletaDTO;
import reto.sc.dto.TransaccionRespuestaDTO;
import reto.sc.excepcion.RecursoNoEncontradoExcepcion;
import reto.sc.modelo.CuentasContables;
import reto.sc.modelo.PartidasContables;
import reto.sc.modelo.Terceros;
import reto.sc.modelo.Transacciones;
import reto.sc.repositorio.CuentasContablesRepositorio;
import reto.sc.repositorio.PartidasRepositorio;
import reto.sc.repositorio.TercerosRepositorio;
import reto.sc.repositorio.TransaccionesRepositorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransaccionesServicios implements ITransaccionesServicios {

    @Autowired
    private TransaccionesRepositorio transaccionesRepositorio;

    @Autowired
    private TercerosRepositorio tercerosRepositorio;

    @Autowired
    private CuentasContablesRepositorio cuentasContablesRepositorio;

    @Autowired
    private PartidasRepositorio partidasRepositorio;



    @Override
    public List<Transacciones> listarTodos() {
        return transaccionesRepositorio.findAll();
    }

    @Override
    public Transacciones buscarTransaccion(Long id) {
        return transaccionesRepositorio.findById(id).orElse(null);

    }

    @Override
    public Transacciones guardarTransaccion(Transacciones transaccion) {
        return transaccionesRepositorio.save(transaccion);
    }

    @Override
    public void eliminarTransaccion(Transacciones transaccion) {
        transaccionesRepositorio.delete(transaccion);

    }
    public Transacciones registrarTransaccionCompleta(TransaccionCompletaDTO dto) {
        System.out.println("DTO recibido: " + dto);

        Transacciones transaccion = new Transacciones();
        transaccion.setFecha(dto.getFecha());
        transaccion.setDescripcion(dto.getDescripcion());

        // Buscar tercero si aplica
        if (dto.getTercerosId() != null) {
            Terceros tercero = tercerosRepositorio.findById(dto.getTercerosId())
                    .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Tercero no encontrado"));
            transaccion.setTerceros(tercero);
            System.out.println("Se asignó el tercero: " + tercero.getNombre());
        }

        // Convertir partidas
        List<PartidasContables> partidas = new ArrayList<>();
        BigDecimal totalDebito = BigDecimal.ZERO;
        BigDecimal totalCredito = BigDecimal.ZERO;

        for (TransaccionCompletaDTO.PartidaDTO p : dto.getPartidas()) {
            PartidasContables partida = new PartidasContables();
            partida.setTipo(p.getTipo());
            partida.setValor(p.getValor());
            partida.setTransaccion(transaccion); // clave para la relación bidireccional

            // Obtener la cuenta contable
            CuentasContables cuenta = cuentasContablesRepositorio.findById(p.getCuentasContablesId())
                    .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Cuenta no encontrada"));

            // Validar si la cuenta está activa
            if (Boolean.FALSE.equals(cuenta.getActivo())) {
                throw new IllegalArgumentException("La cuenta contable con ID " + cuenta.getId() + " está inactiva y no puede usarse en transacciones.");
            }

            partida.setCuentasContables(cuenta);
            partidas.add(partida);

            // Sumar valores según tipo
            if (p.getTipo().equalsIgnoreCase("debito")) {
                totalDebito = totalDebito.add(p.getValor());
            } else if (p.getTipo().equalsIgnoreCase("credito")) {
                totalCredito = totalCredito.add(p.getValor());
            }
        }

        // Validar que cuadren los valores
        if (totalDebito.compareTo(totalCredito) != 0) {
            throw new IllegalArgumentException("Los débitos y créditos no están balanceados");
        }

        transaccion.setPartidas(partidas);
        return transaccionesRepositorio.save(transaccion);
    }
    public TransaccionRespuestaDTO mapearADTO(Transacciones entidad) {
        TransaccionRespuestaDTO dto = new TransaccionRespuestaDTO();
        dto.setId(entidad.getId());
        dto.setFecha(entidad.getFecha());
        dto.setDescripcion(entidad.getDescripcion());

        if (entidad.getTerceros() != null) {
            dto.setNombreTercero(entidad.getTerceros().getNombre());
        } else {
            dto.setNombreTercero("Sin tercero");
        }

        List<TransaccionRespuestaDTO.PartidaRespuestaDTO> partidasDTO = entidad.getPartidas()
                .stream()
                .map(p -> {
                    TransaccionRespuestaDTO.PartidaRespuestaDTO pdto = new TransaccionRespuestaDTO.PartidaRespuestaDTO();
                    pdto.setTipo(p.getTipo());
                    pdto.setValor(p.getValor());
                    pdto.setNombreCuenta(p.getCuentasContables().getNombre());
                    return pdto;
                })
                .toList();

        dto.setPartidas(partidasDTO);
        return dto;
    }




    public List<Transacciones> filtrarTransacciones(String desde, String hasta, Long terceroId) {

        LocalDate fechaDesde = (desde != null && !desde.isBlank()) ? LocalDate.parse(desde) : LocalDate.of(2000, 1, 1);
        LocalDate fechaHasta = (hasta != null && !hasta.isBlank()) ? LocalDate.parse(hasta) : LocalDate.of(2100, 12, 31);


        if (terceroId != null) {
            return transaccionesRepositorio.findByFechaBetweenAndTerceros_Id(fechaDesde, fechaHasta, terceroId);
        } else {
            return transaccionesRepositorio.findByFechaBetween(fechaDesde, fechaHasta);
        }
    }
    public BigDecimal calcularSaldoCuenta(Long cuentaId) {
        BigDecimal debitos = partidasRepositorio.sumValorByCuentaAndTipo(cuentaId, "debito");
        BigDecimal creditos = partidasRepositorio.sumValorByCuentaAndTipo(cuentaId, "credito");

        if (debitos == null) debitos = BigDecimal.ZERO;
        if (creditos == null) creditos = BigDecimal.ZERO;

        return debitos.subtract(creditos);
    }



}
