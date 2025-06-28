package reto.sc.servicios.partidascontables;

import reto.sc.modelo.PartidasContables;

import java.util.List;


public interface IPartidasContablesServicios {

        public List<PartidasContables> listarTodos();
        public PartidasContables buscarPartidasContables(Long id);
        public PartidasContables guardarPartidasContables(PartidasContables partidasContables); // <- CORREGIDO
        public void eliminarPartida(PartidasContables partidasContables);
}



