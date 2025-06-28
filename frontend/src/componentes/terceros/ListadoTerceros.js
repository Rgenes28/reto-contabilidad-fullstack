import axios from 'axios';
import React,{ useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

export default function ListadoTerceros() {

    const urlBase = 'http://localhost:8080/sc-app/terceros';

    const [terceros, setTerceros] = useState([]);

    useEffect(() => {
        cargarTerceros();
    }, []);

    const cargarTerceros = async () => {
        try {
            const response = await axios.get(urlBase);
            console.log("Terceros:", response.data);
            setTerceros(response.data);
        } catch (error) {
            console.error("Error al cargar los terceros:", error);
        }
    }

    const eliminarTercero = async (id) => {
        await axios.delete(`${urlBase}/${id}`)
        cargarTerceros();
    }

    return (
        <div className='container'>
            <div className="container text-center" style={{ margin: '30px' }}>
                <h3>Sistema Contable Basico</h3>
            </div>
            <table className="table table-striped table-hover align-middle">
                <thead className='table-dark'>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Tercero</th>
                        <th scope="col">Tipo de Documento</th>
                        <th scope="col">Número de Documento</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                    //iteramos el arreglo de terceros
                    terceros.map((tercero, indice) => (
                        <tr key={indice}>
                            <th scope="row">{tercero.id}</th>
                            <td>{tercero.nombre}</td>
                            <td>{tercero.tipoDocumento}</td>
                            <td>{tercero.numeroDocumento}</td>
                            <td className='text-center'>
                                <Link to={`/editar/${tercero.id}`} 
                                className='btn btn-warning btn-sm me-3'>
                                    Editar
                                </Link>
                                <button
                                 onClick={()=> eliminarTercero(tercero.id)}
                                    className='btn btn-danger btn-sm'>
                                    Eliminar
                                </button>

                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
        </div>
    )
}