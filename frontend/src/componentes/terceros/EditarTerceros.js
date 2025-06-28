import axios from 'axios';
import React, { useEffect } from 'react'
import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

export default function EditarEmpleado() {

    const urlBase = "http://localhost:8080/sc-app/terceros";

    let navegacion = useNavigate();

    const {id} = useParams();

    const [tercero, setTercero] = useState({
        nombre: "",
        tipoDocumento: "",    
        numeroDocumento: ""
    });

    const{ nombre, tipoDocumento, numeroDocumento } = tercero;


    useEffect(() => {
        cargarTercero();
    }, [id]); //se ejecuta cuando se carga el componente y cuando cambia el id

    const cargarTercero = async () => {
        console.log("ID recibido en editar:", id);
        const response = await axios.get(`${urlBase}/${id}`);
        console.log("Tercero cargado:", response.data);
        setTercero(response.data);
    }

    const cambioDeInput = (e) => {
        //spread operator ... (expandir los atributos del objeto)
        setTercero({...tercero, [e.target.name]: e.target.value });

    }

    const onsubmit = async (e) => {
        e.preventDefault();
        await axios.put(`${urlBase}/${id}`, tercero);
        //redirigir a la página de inicio
        navegacion("/");

    }

    return (
        <div className='container'>
            <div className='container text-center' style={{ margin: '30px' }}>
                <h3>Editar Tercero</h3>
            </div>

                <form onSubmit={(e) => onsubmit(e)}>
                    {/* onsubmit es un evento que se ejecuta cuando se envía el formulario */}
                    {/* e es el evento que se genera al enviar el formulario */}
                    {/* preventDefault es un método que evita que se recargue la página al enviar el formulario */}
                    <div className="mb-3">
                        <label htmlFor="nombre" className="form-label">Nombre</label>
                        <input type="text" className="form-control"
                        id="nombre" name='nombre' required={true}
                        value={nombre} onChange={(e)=> cambioDeInput(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="tipoDocumento" className="form-label">tipoDocumento</label>
                        <input type="text"
                         className="form-control" id="tipoDocumento" name='tipoDocumento'
                         value={tipoDocumento} onChange={(e)=>cambioDeInput(e)}/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="numeroDocumento"
                         className="form-label">Número de Documento</label>
                        <input type="number" step="any"
                        className="form-control" id="numeroDocumento" name='numeroDocumento'
                        value={numeroDocumento} onChange={(e)=>cambioDeInput(e)}/>
                    </div>
                    <div className='text-center'>
                    <button type="submit" 
                    className="btn btn-warning btn-sm me-3">Guardar</button>
                    <a href="/" className="btn btn-danger btn-sm">Regresar</a>
                    </div>
                </form>
        </div>
        
    )
}