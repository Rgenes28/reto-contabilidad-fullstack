package reto.sc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "DTO para registrar una transacción contable completa con sus partidas asociadas")
public class TransaccionCompletaDTO {

    @Schema(description = "Fecha de la transacción", example = "2025-06-26")
    private LocalDate fecha;

    @Schema(description = "Descripción de la transacción", example = "Compra de materiales")
    private String descripcion;

    @Schema(description = "ID del tercero asociado a la transacción", example = "1")
    private Long tercerosId;

    @Schema(description = "Lista de partidas asociadas a la transacción")
    private List<PartidaDTO> partidas;

    @Data
    @Schema(description = "Partida contable que compone la transacción")
    public static class PartidaDTO {

        @Schema(description = "Tipo de la partida: debito o credito", example = "debito")
        private String tipo;

        @Schema(description = "Valor monetario de la partida", example = "100000.00")
        private BigDecimal valor;

        @Schema(description = "ID de la cuenta contable asociada a la partida", example = "2")
        private Long cuentasContablesId;
    }
}

