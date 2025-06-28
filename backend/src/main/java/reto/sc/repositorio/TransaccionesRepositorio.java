package reto.sc.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import reto.sc.modelo.Transacciones;

import java.time.LocalDate;
import java.util.List;

public interface TransaccionesRepositorio extends JpaRepository<Transacciones, Long> {
    List<Transacciones> findByFechaBetween(LocalDate desde, LocalDate hasta);
    List<Transacciones> findByFechaBetweenAndTerceros_Id(LocalDate desde, LocalDate hasta, Long terceroId);


}
