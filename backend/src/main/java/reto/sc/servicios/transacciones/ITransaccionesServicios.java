package reto.sc.servicios.transacciones;

import reto.sc.modelo.Transacciones;

import java.util.List;

public interface ITransaccionesServicios {
   public List<Transacciones> listarTodos();
   public Transacciones buscarTransaccion(Long id);
   public Transacciones guardarTransaccion(Transacciones transaccion);
   public void eliminarTransaccion(Transacciones transaccion);
}
