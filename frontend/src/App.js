import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navegacion from "./plantilla/Navegacion";
import TercerosForm from './componentes/terceros/TercerosForm';
import ListadoTerceros from './componentes/terceros/ListadoTerceros';
import EditarTerceros from './componentes/terceros/EditarTerceros';
import TransaccionesForm from './componentes/transacciones/TransaccionesForm';
import ConsultarTransacciones from './componentes/transacciones/ConsultarTransacciones';
import GestionCuentasContables from './componentes/cuentas/GestionCuentasContables';
import CuentaContablesForm from './componentes/cuentas/CuentaContablesForm';

function App() {
  return (
     <div className="container">
      <BrowserRouter>
        <Navegacion />
        <Routes>
          <Route exact path="/" element={<ListadoTerceros />} />
          <Route exact path="/terceros" element={<TercerosForm/>} />
          <Route exact path="/editar/:id" element={<EditarTerceros />} />
          <Route exact path="/transacciones" element={<TransaccionesForm />} />
          <Route exact path="/consultar-transacciones" element={<ConsultarTransacciones />} />
          <Route exact path="/gestion-cuentas" element={<GestionCuentasContables />} />
          <Route exact path="/crear-cuenta" element={<CuentaContablesForm />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
