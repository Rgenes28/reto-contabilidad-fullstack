
# Sistema Contable Básico

## Descripción del reto

Este sistema contable básico fue desarrollado como parte de una prueba técnica. Permite gestionar transacciones financieras, terceros y cuentas contables, con énfasis en una lógica robusta que garantice la consistencia contable.

A continuación describo cómo abordé cada uno de los puntos solicitados:

---

## ✅ Módulo de Control de Saldos por Cuenta

### ✔ 1. Endpoint e Interfaz para calcular y mostrar el saldo actual

Implementé un endpoint que calcula el saldo actual de una cuenta contable:

```
GET /sc-app/cuentas/{id}/saldo
```

Este calcula el saldo como:

> **Suma de débitos - suma de créditos**

También desarrollé una interfaz React (`GestionCuentasContables.jsx`) que muestra todas las cuentas contables y sus saldos, aplicando colores para facilitar su interpretación:

- 🟢 **Verde**: Saldo positivo  
- 🔴 **Rojo**: Saldo negativo  
- ⚪ **Gris**: Saldo cero

Esto mejora la usabilidad y permite una rápida visualización del estado contable.

---

### ✔ 2. Validación al crear transacciones

Al registrar una nueva transacción, implementé una validación que impide que una cuenta quede con saldo negativo si no tiene permitido hacerlo. Si se intenta registrar una transacción así, se retorna un mensaje de error explicando la razón.

Esta lógica se encuentra en el servicio de transacciones (`TransaccionesServicios.java`), antes de persistir cualquier cambio. Se evalúa el impacto de cada partida en el saldo proyectado.

---

### ✔ 3. Lógica de negocio robusta

Toda la lógica de cálculo y validación fue centralizada en funciones reutilizables para asegurar la claridad y facilidad de mantenimiento:

- `calcularSaldoCuenta(Long cuentaId)`: Calcula el saldo actual de una cuenta.
- Validación al crear transacciones que simula el impacto en el saldo antes de guardar.

Estas funciones garantizan que las reglas del sistema se apliquen consistentemente en todo el backend.

---

## ✅ Gestión de cuentas activas/inactivas

Implementé la funcionalidad para **inactivar** cuentas contables desde la interfaz y el backend. Cuando una cuenta está inactiva, no puede ser usada en nuevas transacciones.

- Endpoint para cambiar el estado:
```
PUT /sc-app/cuentas/{id}/estado
```

- También creé un endpoint adicional para consultar solo las cuentas activas:
```
GET /sc-app/cuentas/activas
```

---

## 🧪 Pruebas desde Postman

Realicé pruebas para validar que:

- No se permiten transacciones con cuentas inactivas.
- No se permite dejar saldo negativo en cuentas que no lo permiten.
- Se puede activar/desactivar cuentas correctamente.

---

## 🚀 Tecnologías utilizadas

- **Backend:** Java + Spring Boot  
- **Frontend:** React + Bootstrap  
- **Base de datos:** PostgreSQL  
- **Herramientas de prueba:** Postman

---

## 🙋‍♂️ Autor

Ronaldo Genes  
