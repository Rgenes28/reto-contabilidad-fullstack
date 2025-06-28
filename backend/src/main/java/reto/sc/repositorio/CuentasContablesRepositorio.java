package reto.sc.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import reto.sc.modelo.CuentasContables;

import java.util.List;

public interface CuentasContablesRepositorio extends JpaRepository<CuentasContables, Long> {
    List<CuentasContables> findByActivoTrue();

}
