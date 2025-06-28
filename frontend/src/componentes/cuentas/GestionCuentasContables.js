import React, { useEffect, useState } from 'react';
import axios from 'axios';

const GestionCuentasContables = () => {
    const [cuentas, setCuentas] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [saldos, setSaldos] = useState({});

    const baseUrl = 'http://localhost:8080/sc-app';

    useEffect(() => {
        cargarCuentas();
    }, []);

    useEffect(() => {
        if (cuentas.length > 0) {
            cuentas.forEach((cuenta) => {
                fetch(`http://localhost:8080/sc-app/cuentas/${cuenta.id}/saldo`)
                    .then(res => res.json())
                    .then(saldo => {
                        setSaldos(prev => ({ ...prev, [cuenta.id]: saldo }));
                    });
            });
        }
    }, [cuentas]); // este hook se dispara solo cuando cuentas ya esté cargado


    const cargarCuentas = async () => {
        try {
            const response = await axios.get(`${baseUrl}/cuentas`);
            setCuentas(response.data);
            setCargando(false);
        } catch (error) {
            console.error("Error al cargar las cuentas:", error);
            setCargando(false);
        }
    };

    const cambiarEstado = async (id, nuevoEstado) => {
        try {
            await axios.put(`${baseUrl}/cuentas/${id}/estado`, { activo: nuevoEstado });
            cargarCuentas(); // recargar después del cambio
        } catch (error) {
            console.error("Error al cambiar estado:", error);
        }
    };
    const getColor = (saldo) => {
        if (saldo > 0) return 'green';
        if (saldo < 0) return 'red';
        return 'gray';
    };
    const formatearVisual = (valor) => {
        const limpio = String(valor).replace(/\D/g, '');
        const numero = parseInt(limpio, 10);
        if (isNaN(numero)) return '';
        return new Intl.NumberFormat('es-CO').format(numero);
    };



    if (cargando) return <div className="text-center mt-4">Cargando cuentas...</div>;

    return (
        <div className="container mt-4">
            <h3 className="mb-3">Gestión de Cuentas Contables</h3>
            <table className="table table-bordered table-hover">
                <thead className="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Código</th>
                        <th>Tipo</th>
                        <th>Saldo Negativo</th>
                        < th>Saldo</th>
                        <th>Estado</th>
                        <th>Acción</th>
                    </tr>
                </thead>
                <tbody>
                    {cuentas.map(cuenta => (
                        <tr key={cuenta.id}>
                            <td>{cuenta.id}</td>
                            <td>{cuenta.nombre}</td>
                            <td>{cuenta.codigo}</td>
                            <td>{cuenta.tipo}</td>
                            <td>{cuenta.permiteSaldoNegativo ? 'Sí' : 'No'}</td>
                            <td style={{ color: getColor(saldos[cuenta.id]) }}>
                                {saldos[cuenta.id] !== undefined ? formatearVisual(saldos[cuenta.id]) : 'Cargando...'}
                            </td>


                            <td>
                                <span className={`badge ${cuenta.activo ? 'bg-success' : 'bg-secondary'}`}>
                                    {cuenta.activo ? 'Activa' : 'Inactiva'}
                                </span>
                            </td>
                            <td>
                                <button
                                    className={`btn btn-sm ${cuenta.activo ? 'btn-danger' : 'btn-primary'}`}
                                    onClick={() => cambiarEstado(cuenta.id, !cuenta.activo)}
                                >
                                    {cuenta.activo ? 'Inactivar' : 'Activar'}
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default GestionCuentasContables;
