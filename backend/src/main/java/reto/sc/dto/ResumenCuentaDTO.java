package reto.sc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "DTO que representa el resumen del saldo por cuenta contable")
public class ResumenCuentaDTO {

    @Schema(description = "ID único de la cuenta contable", example = "2")
    private Long idCuenta;

    @Schema(description = "Nombre de la cuenta contable", example = "Caja General")
    private String nombreCuenta;

    @Schema(description = "Código contable de la cuenta", example = "1105")
    private String codigoCuenta;

    @Schema(description = "Saldo actual de la cuenta", example = "150000.00")
    private BigDecimal saldo;
}
