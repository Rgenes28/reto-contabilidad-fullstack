import React from 'react';
import { Link } from 'react-router-dom';
export default function Navegacion() {
    return (
         <div classNameName='container'>
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/">Sistema Contable Basico</a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <a className="nav-link active" aria-current="page" href="/">Inicio</a>    
                            </li>
                            <li className="nav-item">
                                 <Link className="nav-link" to="/terceros">Agregar Tercero</Link>
                            </li>
                            <li className="nav-item">
                                 <Link className="nav-link" to="/transacciones">Agregar Transacción</Link>
                            </li>
                            <li className="nav-item">
                                 <Link className="nav-link" to="/consultar-transacciones">Consultar Transacciones</Link>
                            </li>
                             <li className="nav-item">
                                 <Link className="nav-link" to="/gestion-cuentas">Gestion de Cuentas</Link>
                            </li>
                            <li className="nav-item">
                                 <Link className="nav-link" to="/crear-cuenta">Crear Cuenta Contable</Link>
                            </li>

                        </ul>
                    </div>
                </div>
            </nav>
        </div>

    )
}