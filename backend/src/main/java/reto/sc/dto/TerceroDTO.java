package reto.sc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerceroDTO {
        private Long id;
        private String nombre;
        private String tipoDocumento;
        private String numeroDocumento;
}


