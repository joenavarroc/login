# Proyecto Login

Aplicación backend desarrollada en Java con Spring Boot para autenticación segura mediante JWT y OAuth. Incluye funcionalidades de registro, inicio de sesión, recuperación de credenciales vía correo electrónico y seguridad con Spring Security.

---

## Tecnologías

- Lenguaje: Java, JavaScript, HTML, CSS
- Frameworks: Spring Boot, Spring Security, OAuth 2.0
- Seguridad: JWT para autenticación
- Base de datos: MySQL
- Envío de correos para recuperación de contraseña
- Contenedores: Docker y Docker Compose

---

## Funcionalidades principales

- Registro y login con autenticación JWT y OAuth (Google, etc.)
- Recuperación de contraseña mediante correo electrónico
- Seguridad avanzada con Spring Security y JWT
- Interfaz web básica para autenticación (login y registro)
- Contenedores Docker para despliegue y orquestación

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio  
   ```bash
   git clone https://github.com/joenavarroc/login.git
   cd login
Configurar la base de datos MySQL (modificar application.properties con tus credenciales)

Construir y ejecutar con Docker Compose
docker-compose up --build
Acceder a la aplicación en
http://localhost:8080

Uso
Registrar un nuevo usuario

Iniciar sesión con usuario/contraseña o mediante OAuth (Google,Github,FaceBook)

Recuperar contraseña si se olvida mediante correo electrónico
