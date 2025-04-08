
# Reto Técnico Civa - Backend

## Características
- 🔐 Autenticación basada en JWT
- 🚌 Gestión de buses (operaciones CRUD)
- 🤵 Gestión de usuarios
- 💾 Inicialización de la base de datos con datos de ejemplo

## Tecnologías Utilizadas
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Hibernate / JPA
- MySQL
- Maven
- Lombok


## Configuración del Proyecto

### 1. Clonar el Repositorio
```bash
git clone https://github.com/drkdevv1/prueba_civa
cd prueba_civa
```

### 2. Configuración de la Base de Datos
Configura la conexión en `application.properties`, no es necesario crear la db:

```properties
# Configuración de la Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/prueba_civa_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

> **Nota**: Reemplaza `tu_contraseña` con tu contraseña real de MySQL.

### 3. Esquema de la Base de Datos
La aplicación creará automáticamente las tablas necesarias al iniciar gracias a `spring.jpa.hibernate.ddl-auto=update`. El esquema requerido incluye:

- `usuarios`: Información de usuarios
- `marcas`: Marcas de buses
- `buses`: Detalles de los buses

### 4. Construir el Proyecto
```bash
mvn clean package
```

### 5. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación iniciará en `http://localhost:8080` e inicializará automáticamente la base de datos con datos de ejemplo si está vacía.

## Autenticación

### Usuario por Defecto
Se crea un usuario por defecto al iniciar por primera vez:
- Usuario: `admin`
- Contraseña: `password`
- Correo: `admin@example.com`

### Endpoints de Autenticación
- Login: `POST /api/auth/login`
  ```json
  {
    "username": "admin",
    "password": "password"
  }
  ```

- Registro: `POST /api/auth/register`
  ```json
  {
    "username": "nuevousuario",
    "password": "password",
    "email": "nuevousuario@example.com",
    "nombreCompleto": "Nuevo Usuario"
  }
  ```

## Endpoints de la API

### Buses
- Obtener todos los buses: `GET /api/v1/bus`
- Obtener bus por ID: `GET /api/v1/bus/{id}`

Todos los endpoints de gestión de buses requieren autenticación con JWT. Incluye el token en el encabezado Authorization:
```
Authorization: Bearer <tu_token_jwt>
```

