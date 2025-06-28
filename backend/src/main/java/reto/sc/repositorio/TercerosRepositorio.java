package reto.sc.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import reto.sc.modelo.Terceros;

import java.util.List;

public interface TercerosRepositorio extends JpaRepository<Terceros, Long> {
    List<Terceros> findAllByOrderByIdAsc();
}
