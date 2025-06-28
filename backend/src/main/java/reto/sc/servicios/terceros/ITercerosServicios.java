package reto.sc.servicios.terceros;

import reto.sc.modelo.Terceros;

import java.util.List;

public interface ITercerosServicios {
    public List<Terceros> listarTodos();

    public Terceros buscarTercero(Long id);

    public Terceros guardarTercero(Terceros tercero);

    public void eliminarTercero(Long id );
}
