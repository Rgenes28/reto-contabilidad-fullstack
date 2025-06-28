import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router';

export default function TercerosForm() {

  let navegacion = useNavigate();


  const [tercero, setTercero] = useState({
    nombre: '',
    tipoDocumento: '',
    numeroDocumento: ''
  });

  const{ nombre, tipoDocumento, numeroDocumento } = tercero;

  const cambioDeInput = (e) => {
    // Actualiza el estado del tercero con el valor del input
    setTercero({ ...tercero, [e.target.name]: e.target.value });
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    const urlBase = 'http://localhost:8080/sc-app/terceros';
    await axios.post(urlBase, tercero);
    navegacion('/');
  };

  return (
    <div className="container">
      <div className='container text-center' style={{ margin:'30px' }}>
        <h3>Agregar Tercero</h3>

        <form onSubmit={(e) => onSubmit(e)}>
          <div className="mb-3">
                        <label htmlFor="nombre" className="form-label">Nombre</label>
                        <input type="text" className="form-control"
                        id="nombre" name='nombre' required={true}
                        value={nombre} onChange={(e)=> cambioDeInput(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="tipoDocumento" className="form-label">Tipo de Documento</label>
                        <input type="text" className="form-control"
                         id="tipoDocumentoto" name='tipoDocumento'
                         value={tipoDocumento} onChange={(e)=>cambioDeInput(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="numeroDocumento" className="form-label">Número de Documento</label>
                        <input type="text" step="any"
                        className="form-control" id="numeroDocumento" name='numeroDocumento'
                        value={numeroDocumento} onChange={(e)=>cambioDeInput(e)}/>
                    </div>
                    <div className='text-center'>
                    <button type="submit" 
                    className="btn btn-warning btn-sm me-3">Agregar</button>
                    <a href="/" className="btn btn-danger btn-sm">Regresar</a>
                    </div>
        </form>
      </div>
      </div>
  )
}
