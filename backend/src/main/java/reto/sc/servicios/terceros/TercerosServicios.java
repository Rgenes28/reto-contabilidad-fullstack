package reto.sc.servicios.terceros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reto.sc.modelo.Terceros;
import reto.sc.repositorio.TercerosRepositorio;

import java.util.List;

@Service
public class TercerosServicios implements ITercerosServicios {

    @Autowired
    private TercerosRepositorio tercerosRepositorio;

    @Override
    public List<Terceros> listarTodos() {
        return tercerosRepositorio.findAll();
    }

    @Override
    public Terceros buscarTercero(Long id) {
        return tercerosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tercero no encontrado con ID: " + id));
    }

    @Override
    public Terceros guardarTercero(Terceros tercero) {
        return tercerosRepositorio.save(tercero);
    }

    @Override
    public void eliminarTercero(Long id) {
        if (!tercerosRepositorio.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Tercero con ID " + id + " no existe.");
        }
        tercerosRepositorio.deleteById(id);
    }
}

