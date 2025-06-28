
# 💼 Sistema Contable Básico
Este sistema contable básico fue desarrollado como parte de una prueba técnica Full Stack. Permite gestionar transacciones financieras, terceros y cuentas contables, asegurando siempre la consistencia contable y una experiencia de usuario clara.

# ⚙️ Levantamiento del sistema
Este proyecto utiliza Docker para facilitar el despliegue de los tres componentes (backend, frontend y base de datos) con un solo comando gracias al archivo docker-compose.yml.

▶️ Instrucciones rápidas

en terminal ejecutar 

docker-compose up --build

Esto levantará:

🌐 Frontend (React + Bootstrap)

⚙️ Backend (Spring Boot)

🛢️ Base de datos PostgreSQL

# ✅ Funcionalidades implementadas
1. 🔢 Control de saldos por cuenta
   
🛠️ Endpoint:GET /sc-app/cuentas/{id}/saldo

Calcula: Suma de débitos - suma de créditos

Utiliza lógica robusta para garantizar que las reglas contables se cumplan antes de registrar una transacción.

# 📘 Documentación de la API - Swagger UI
Este proyecto cuenta con documentación automática generada mediante Swagger (Springdoc OpenAPI).

Una vez levantado el backend, puedes acceder a la interfaz interactiva para probar y explorar los endpoints:

📎 URL por defecto:
http://localhost:8080/swagger-ui.html
o
http://localhost:8080/swagger-ui/index.html

La documentación se genera automáticamente escaneando los controladores REST, lo cual facilita la integración y pruebas desde el navegador.

# 🎨 Interfaz:
Componente React GestionCuentasContables.jsx con colores según saldo:

🟢 Verde: saldo positivo

🔴 Rojo: saldo negativo

⚪ Gris: saldo en cero

# 2. ✅ Validación de transacciones
Antes de guardar una transacción, se valida que:

Las cuentas contables estén activas

No se permita saldo negativo en cuentas que no lo permiten

Toda esta lógica vive en TransaccionesServicios.java y es reutilizable y centralizada.

# 3. 🔒 Gestión de cuentas activas/inactivas
Puedes activar o desactivar cuentas desde el frontend o backend.

Las cuentas inactivas no pueden ser usadas en nuevas transacciones.

Endpoints relevantes:
PUT /sc-app/cuentas/{id}/estado
GET /sc-app/cuentas/activas
🧪 Pruebas realizadas
Usé Postman para validar que:

❌ No se permiten transacciones con cuentas inactivas

❌ No se permite saldo negativo cuando no está permitido

✅ Se pueden activar o inactivar cuentas desde los endpoints

# 🛠️ Tecnologías utilizadas
Capa	Tecnología

Backend	Java + Spring Boot

Frontend	React + Bootstrap

Base Datos	PostgreSQL

Entorno	Docker + Docker Compose

Pruebas	Postman

🙋‍♂️ Autor
Ronaldo Genes
