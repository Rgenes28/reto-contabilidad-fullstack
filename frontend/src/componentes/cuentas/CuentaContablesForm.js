import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function CuentaContableForm() {
  const navigate = useNavigate();

  const [formulario, setFormulario] = useState({
    codigo: '',
    nombre: '',
    tipo: '',
    permiteSaldoNegativo: false,
    activo: true
  });

  const tipos = ['ACTIVO', 'PASIVO', 'PATRIMONIO', 'INGRESO', 'GASTO'];

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormulario({
      ...formulario,
      [name]: type === 'checkbox' ? checked : value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/sc-app/cuentas', formulario);
      alert('Cuenta contable creada exitosamente');
      navigate('/'); // Redirige al inicio o a donde tengas el listado
    } catch (error) {
      console.error('Error al crear la cuenta:', error);
      alert('Ocurrió un error al guardar la cuenta');
    }
  };

  return (
    <div className="container mt-4">
      <h3>Crear Nueva Cuenta Contable</h3>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Código</label>
          <input
            type="text"
            className="form-control"
            name="codigo"
            value={formulario.codigo}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Nombre</label>
          <input
            type="text"
            className="form-control"
            name="nombre"
            value={formulario.nombre}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Tipo</label>
          <select
            className="form-select"
            name="tipo"
            value={formulario.tipo}
            onChange={handleChange}
            required
          >
            <option value="">Seleccione el tipo</option>
            {tipos.map((tipo) => (
              <option key={tipo} value={tipo}>
                {tipo}
              </option>
            ))}
          </select>
        </div>

        <div className="form-check mb-3">
          <input
            className="form-check-input"
            type="checkbox"
            name="permiteSaldoNegativo"
            checked={formulario.permiteSaldoNegativo}
            onChange={handleChange}
          />
          <label className="form-check-label">
            Permite saldo negativo
          </label>
        </div>

        <div className="form-check mb-3">
          <input
            className="form-check-input"
            type="checkbox"
            name="activo"
            checked={formulario.activo}
            onChange={handleChange}
          />
          <label className="form-check-label">
            Activa
          </label>
        </div>

        <div className="text-center">
          <button type="submit" className="btn btn-primary">
            Crear Cuenta
          </button>
        </div>
      </form>
    </div>
  );
}
