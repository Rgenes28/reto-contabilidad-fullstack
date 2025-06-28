package reto.sc.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reto.sc.modelo.CuentasContables;
import reto.sc.modelo.PartidasContables;

import java.math.BigDecimal;
import java.util.List;

public interface PartidasRepositorio extends JpaRepository<PartidasContables, Long> {
    List<PartidasContables> findByCuentasContablesId(Long cuentaId);

    List<PartidasContables> findByCuentasContables(CuentasContables cuenta);
    @Query("SELECT SUM(p.valor) FROM PartidasContables p WHERE p.cuentasContables.id = :cuentaId AND p.tipo = :tipo")
    BigDecimal sumValorByCuentaAndTipo(@Param("cuentaId") Long cuentaId, @Param("tipo") String tipo);

}
