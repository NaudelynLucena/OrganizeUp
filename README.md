# OrganizeUp

OrganizeUp es una aplicación web diseñada para ayudar a los usuarios a gestionar sus tareas, grupos y recompensas de manera eficiente. La aplicación permite a los usuarios crear y completar tareas, unirse a grupos, y acumular puntos que pueden ser canjeados por recompensas.

## Características

- **Autenticación de Usuarios**: Registro y login de usuarios.
- **Dashboard Principal**: Vista de bienvenida con información del usuario y acceso a grupos y tareas.
- **Gestión de Grupos**: Crear, unirse y listar grupos.
- **Gestión de Tareas**: Crear, listar y completar tareas.
- **Sistema de Recompensas**: Acumular puntos por tareas completadas y canjearlos por recompensas.

## Tecnologías Utilizadas

- **Frontend**: React.js
- **Backend**: Spring Boot
- **Base de Datos**: MySQL
- **Autenticación**: JWT (JSON Web Tokens)

## Instalación

### Requisitos Previos

- [Node.js](https://nodejs.org/) (v14 o superior)
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (v11 o superior)
- [MySQL](https://www.mysql.com/) (v5.7 o superior)

### Clonar el Repositorio

```bash
git clone https://github.com/tu_usuario/organizeup.git
cd organizeup
Configuración del Backend
Navega a la carpeta del backend:

bash
cd backend
Configura la base de datos en src/main/resources/application.properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/organizeup?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
Compila y ejecuta el backend:

bash
./mvnw spring-boot:run

Configuración del Frontend

Navega a la carpeta del frontend:
bash
cd frontend

Instala las dependencias:
bash
npm install

Ejecuta la aplicación:
bash
npm start

Uso
Regístrate como nuevo usuario o inicia sesión si ya tienes una cuenta.
Accede al dashboard para ver tu información y opciones.
Crea grupos y tareas, y completa las tareas para acumular puntos.
Consulta tus puntos acumulados y canjea recompensas.
Contribuciones
Las contribuciones son bienvenidas. Si deseas contribuir, por favor sigue estos pasos:

Haz un fork del proyecto.
Crea una nueva rama (git checkout -b feature/nueva-caracteristica).
Realiza tus cambios y haz commit (git commit -m 'Añadir nueva característica').
Haz push a la rama (git push origin feature/nueva-caracteristica).
Abre un Pull Request.
Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

Contacto
Si tienes alguna pregunta o sugerencia, no dudes en contactarme:

Email: naulunau@gmail.com
