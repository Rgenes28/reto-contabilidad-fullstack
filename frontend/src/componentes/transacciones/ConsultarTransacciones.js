import React, { useState, useEffect } from "react";
import axios from "axios";

export default function ConsultarTransacciones() {

  const [filtros, setFiltros] = useState({
    desde: '',
    hasta: '',
    terceroId: ''
  });

  const [terceros, setTerceros] = useState([]);
  const [transacciones, setTransacciones] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/sc-app/terceros')
      .then(res => setTerceros(res.data))
      .catch(err => console.error('Error al cargar terceros:', err));

    buscarTransaccionesSinEvento();
  }, []);

  const handleChange = (e) => {
    setFiltros({ ...filtros, [e.target.name]: e.target.value });
  };

  const buscarTransaccionesSinEvento = async () => {
    try {
      const res = await axios.get('http://localhost:8080/sc-app/transacciones/filtrar');
      setTransacciones(res.data);
    } catch (error) {
      console.error('Error al consultar transacciones:', error);
    }
  };

  const buscarTransacciones = async (e) => {
    e.preventDefault();
    const { desde, hasta, terceroId } = filtros;
    const params = {};
    if (desde) params.desde = desde;
    if (hasta) params.hasta = hasta;
    if (terceroId) params.terceroId = Number(terceroId);

    console.log("Valores enviados a la API:", params);
    try {
      const res = await axios.get('http://localhost:8080/sc-app/transacciones/filtrar', { params });
      setTransacciones(res.data);
    } catch (error) {
      console.error('Error al consultar transacciones:', error);
    }
  };

  return (
    <div className="container mt-4">
      <h3 className="mb-3">Consultar Transacciones</h3>

      <form onSubmit={buscarTransacciones} className="mb-4">
        <div className="row mb-3">
          <div className="col">
            <label>Desde</label>
            <input type="date" name="desde" value={filtros.desde} onChange={handleChange} className="form-control" />
          </div>
          <div className="col">
            <label>Hasta</label>
            <input type="date" name="hasta" value={filtros.hasta} onChange={handleChange} className="form-control" />
          </div>
          <div className="col">
            <label>Tercero</label>
            <select name="terceroId" value={filtros.terceroId} onChange={handleChange} className="form-select">
              <option value="">-- Todos --</option>
              {terceros.map(t => (
                <option key={t.id} value={t.id}>{t.nombre}</option>
              ))}
            </select>
          </div>
        </div>
        <button type="submit" className="btn btn-primary">Buscar</button>
      </form>

      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Fecha</th>
            <th>Descripción</th>
            <th>Tercero</th>
            <th>Partidas</th>
          </tr>
        </thead>
        <tbody>
          {transacciones.map((tx) => (
            <tr key={tx.id}>
              <td>{tx.fecha}</td>
              <td>{tx.descripcion}</td>
              <td>{tx.nombreTercero || 'Sin tercero'}</td>
              <td>{tx.partidas ? tx.partidas.length : 0}</td>
            </tr>

          ))}
        </tbody>

      </table>
    </div>
  );
}
