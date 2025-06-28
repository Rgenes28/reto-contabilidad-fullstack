import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function TransaccionForm() {
  const navigate = useNavigate();

  const [formulario, setFormulario] = useState({
    fecha: '',
    descripcion: '',
    tercerosId: '',
    partidas: [
      { tipo: 'debito', valor: '', cuentasContablesId: '' },
      { tipo: 'credito', valor: '', cuentasContablesId: '' },
    ]
  });

  const [terceros, setTerceros] = useState([]);
  const [cuentasContables, setCuentasContables] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/sc-app/terceros')
      .then(res => setTerceros(res.data))
      .catch(err => console.error('Error cargando terceros', err));

    axios.get("http://localhost:8080/sc-app/cuentas/activas")
      .then(res => setCuentasContables(res.data))
      .catch(err => console.error("Error al obtener cuentas activas", err));
  }, []);

  const handleChange = (e) => {
    setFormulario({ ...formulario, [e.target.name]: e.target.value });
  };

  const handlePartidaChange = (index, e) => {
    const nuevasPartidas = [...formulario.partidas];
    nuevasPartidas[index][e.target.name] = e.target.value;
    setFormulario({ ...formulario, partidas: nuevasPartidas });
  };

  const agregarPartida = () => {
    setFormulario({
      ...formulario,
      partidas: [...formulario.partidas, { tipo: 'debito', valor: '', cuentasContablesId: '' }]
    });
  };

  const quitarPartida = (index) => {
    const nuevas = formulario.partidas.filter((_, i) => i !== index);
    setFormulario({ ...formulario, partidas: nuevas });
  };

  const validarBalance = () => {
    const totalDebito = formulario.partidas
      .filter(p => p.tipo === 'debito')
      .reduce((sum, p) => sum + parseFloat(p.valor || 0), 0);

    const totalCredito = formulario.partidas
      .filter(p => p.tipo === 'credito')
      .reduce((sum, p) => sum + parseFloat(p.valor || 0), 0);

    return totalDebito === totalCredito;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validarBalance()) {
      alert('El total de débitos y créditos no coincide.');
      return;
    }
 
    console.log("Datos enviados:", formulario);
    const url = 'http://localhost:8080/sc-app/transacciones-completas';
    try {
      await axios.post(url, formulario);
      alert('Transacción registrada exitosamente');
      navigate('/');
    } catch (error) {
      console.error('Error al registrar transacción:', error);
    }

  };
   const formatearVisual = (valor) => {
    const limpio = String(valor).replace(/\D/g, '');
    const numero = parseInt(limpio, 10);
    if (isNaN(numero)) return '';
    return new Intl.NumberFormat('es-CO').format(numero);
  };

  const handleFormattedChange = (index, e) => {
    const valorSinFormato = e.target.value.replace(/\./g, '').replace(/[^0-9]/g, '');
    const nuevasPartidas = [...formulario.partidas]; // aquí usamos formulario del useState
    nuevasPartidas[index].valor = valorSinFormato;
    setFormulario({ ...formulario, partidas: nuevasPartidas });
  };

  return (
    <div className="container mt-4">
      <h3>Registrar Transacción</h3>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Fecha</label>
          <input type="date" name="fecha" className="form-control"
            value={formulario.fecha} onChange={handleChange} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Descripción</label>
          <input type="text" name="descripcion" className="form-control"
            value={formulario.descripcion} onChange={handleChange} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Tercero</label>
          <select name="tercerosId" className="form-select"
            value={formulario.tercerosId} onChange={handleChange} required>
            <option value="">Seleccione un tercero</option>
            {terceros.map(t => (
              <option key={t.id} value={t.id}>{t.nombre}</option>
            ))}
          </select>
        </div>

        <h5>Partidas Contables</h5>
        {formulario.partidas.map((partida, index) => (
          <div key={index} className="border p-3 mb-3">
            <div className="mb-2">
              <label className="form-label">Tipo</label>
              <select name="tipo" className="form-select"
                value={partida.tipo} onChange={(e) => handlePartidaChange(index, e)} required>
                <option value="debito">Débito</option>
                <option value="credito">Crédito</option>
              </select>
            </div>

            <div className="mb-2">
              <label className="form-label">Valor</label>
              <input type="text" name="valor" className="form-control" value={formatearVisual(partida.valor)}
                onChange={(e) => handleFormattedChange(index, e)} required />

            </div>

            <div className="mb-2">
              <label className="form-label">Cuenta Contable</label>
              <select name="cuentasContablesId" className="form-select"
                value={partida.cuentasContablesId} onChange={(e) => handlePartidaChange(index, e)} required>
                <option value="">Seleccione una cuenta</option>
                {cuentasContables.map(c => (
                  <option key={c.id} value={c.id}>
                    {c.codigo} - {c.nombre}
                  </option>
                ))}
              </select>
            </div>

            <button type="button" className="btn btn-danger btn-sm" onClick={() => quitarPartida(index)}>
              Quitar Partida
            </button>
          </div>
        ))}

        <button type="button" className="btn btn-secondary btn-sm mb-3" onClick={agregarPartida}>
          Agregar Partida
        </button>

        <div className="text-center">
          <button type="submit" className="btn btn-primary">Registrar Transacción</button>
        </div>
      </form>
    </div>
  );
}
