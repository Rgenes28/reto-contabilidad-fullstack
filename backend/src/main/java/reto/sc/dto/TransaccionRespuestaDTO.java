package reto.sc.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TransaccionRespuestaDTO {
    private Long id;
    private LocalDate fecha;
    private String descripcion;
    private String nombreTercero; // No el ID
    private List<PartidaRespuestaDTO> partidas;

    @Data
    public static class PartidaRespuestaDTO {
        private String tipo;
        private BigDecimal valor;
        private String nombreCuenta; // No el ID
    }
}
