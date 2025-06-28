package reto.sc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "DTO que representa el saldo detallado de una cuenta contable")
public class SaldoCuentaDTO {

    @Schema(description = "ID único de la cuenta contable", example = "2")
    private Long cuentaId;

    @Schema(description = "Total acumulado de débitos en la cuenta", example = "200000.00")
    private BigDecimal totalDebito;

    @Schema(description = "Total acumulado de créditos en la cuenta", example = "50000.00")
    private BigDecimal totalCredito;

    @Schema(description = "Saldo resultante de la cuenta (débito - crédito)", example = "150000.00")
    private BigDecimal saldo;
}
