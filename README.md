# Forohub API

## Descripción del Proyecto

Forohub es una API RESTful desarrollada con Spring Boot que simula un foro de discusión. Permite a los usuarios interactuar con tópicos (crear, listar, ver detalles, actualizar y eliminar) de manera segura, utilizando autenticación basada en JSON Web Tokens (JWT) y Spring Security.

## Características

- **CRUD Completo para Tópicos:**
  - Creación de nuevos tópicos.
  - Listado de todos los tópicos con paginación, ordenamiento y filtrado.
  - Visualización detallada de un tópico específico por ID.
  - Actualización de tópicos existentes.
  - Eliminación de tópicos.
- **Autenticación y Autorización:**
  - Sistema de autenticación de usuarios mediante Spring Security y JWT.
  - Endpoints protegidos que requieren un token JWT válido.
- **Gestión de Base de Datos:**
  - Migraciones de base de datos gestionadas con Flyway.
  - Integración con PostgreSQL.
- **Validación de Datos:**
  - Validación de entrada de datos para asegurar la integridad.

## Tecnologías Utilizadas

- **Java 24**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Flyway** (para migraciones de base de datos)
- **JWT (JSON Web Tokens)** (para autenticación)
- **Maven** (gestor de dependencias)
- **Lombok** (para reducir código boilerplate)

## Configuración y Ejecución del Proyecto

### Prerrequisitos

Asegúrate de tener instalado lo siguiente:

- **Java Development Kit (JDK) 24**
- **Apache Maven**
- **PostgreSQL** (y un servidor de base de datos en ejecución)

### Configuración de la Base de Datos

1.  **Crea una base de datos** en PostgreSQL llamada `forohub`.
2.  **Configura las credenciales de la base de datos** en el archivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/forohub
    spring.datasource.username=postgres
    spring.datasource.password=tu_contrasena_postgres
    ```

    **Importante:** Reemplaza `tu_contrasena_postgres` con la contraseña de tu usuario de PostgreSQL.

3.  **Ejecuta las migraciones de Flyway:**
    Flyway se ejecutará automáticamente al iniciar la aplicación por primera vez, creando las tablas necesarias y poblando con datos iniciales.

### Configuración de la Clave Secreta JWT

Asegúrate de tener una clave secreta definida en `src/main/resources/application.properties` para la generación y validación de JWT:

```properties
api.security.secret=tu_clave_secreta_jwt_aqui
```

**Importante:** Reemplaza `tu_clave_secreta_jwt_aqui` con una cadena de texto larga y segura. **No uses esta clave en producción.**

### Ejecución de la Aplicación

1.  Navega a la raíz del proyecto `forohub` en tu terminal.
2.  Ejecuta el siguiente comando para iniciar la aplicación Spring Boot:

    ```bash
    mvn spring-boot:run
    ```

    La aplicación se iniciará en `http://localhost:8080`.

## Endpoints de la API

Todos los endpoints, excepto `/login`, requieren autenticación mediante un **Bearer Token (JWT)** en el encabezado `Authorization`.

### 1. Autenticación de Usuario

- **URL:** `/login`
- **Método:** `POST`
- **Descripción:** Autentica a un usuario y devuelve un token JWT.
- **Body (JSON):**
  ```json
  {
    "correoElectronico": "admin@forohub.com",
    "contrasena": "123456"
  }
  ```
- **Respuesta Exitosa (200 OK):**
  ```json
  {
    "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```

### 2. Creación de Tópico

- **URL:** `/topicos`
- **Método:** `POST`
- **Descripción:** Crea un nuevo tópico. Requiere autenticación.
- **Body (JSON):**
  ```json
  {
    "titulo": "Título del nuevo tópico",
    "mensaje": "Contenido del mensaje del tópico.",
    "autorId": 1,
    "cursoId": 1
  }
  ```
- **Respuesta Exitosa (201 Created):** Retorna el objeto del tópico creado.

### 3. Listado de Tópicos

- **URL:** `/topicos`
- **Método:** `GET`
- **Descripción:** Lista todos los tópicos con paginación, ordenamiento y filtrado. Requiere autenticación.
- **Parámetros de Consulta (Opcionales):**
  - `page`: Número de página (por defecto 0).
  - `size`: Cantidad de elementos por página (por defecto 10).
  - `sort`: Campo por el cual ordenar (ej. `fechaCreacion,desc`).
  - `nombreCurso`: Filtra por el nombre del curso.
  - `anio`: Filtra por el año de creación del tópico.
- **Ejemplo de URL:** `/topicos?page=0&size=5&sort=fechaCreacion,asc&nombreCurso=Spring Boot&anio=2025`
- **Respuesta Exitosa (200 OK):** Retorna una página de objetos de tópico.

### 4. Detalle de Tópico

- **URL:** `/topicos/{id}`
- **Método:** `GET`
- **Descripción:** Obtiene los detalles de un tópico específico por su ID. Requiere autenticación.
- **Respuesta Exitosa (200 OK):** Retorna el objeto del tópico.
- **Respuesta Fallida (404 Not Found):** Si el tópico no existe.

### 5. Actualización de Tópico

- **URL:** `/topicos/{id}`
- **Método:** `PUT`
- **Descripción:** Actualiza un tópico existente por su ID. Requiere autenticación.
- **Body (JSON):** Puedes enviar solo los campos que deseas actualizar.
  ```json
  {
    "titulo": "Título actualizado",
    "mensaje": "Mensaje actualizado.",
    "status": "CERRADO"
  }
  ```
- **Respuesta Exitosa (200 OK):** Retorna el objeto del tópico actualizado.
- **Respuestas Fallidas:**
  - `404 Not Found`: Si el tópico no existe.
  - `409 Conflict`: Si el título y mensaje actualizados ya existen en otro tópico.

### 6. Eliminación de Tópico

- **URL:** `/topicos/{id}`
- **Método:** `DELETE`
- **Descripción:** Elimina un tópico existente por su ID. Requiere autenticación.
- **Respuesta Exitosa (204 No Content):** Si la eliminación fue exitosa.
- **Respuesta Fallida (404 Not Found):** Si el tópico no existe.

## Capturas de Pantalla / Demostración

```markdown
### Autenticación Exitosa
<img width="649" height="572" alt="image" src="https://github.com/user-attachments/assets/d48db195-c394-46e8-96ea-f7a9b643a9ff" />


### Listado de Tópicos

![Listado de Tópicos en Postman](images/list_topics.png)
```

## Contribuciones

Si deseas contribuir a este proyecto, por favor, sigue los estándares de código existentes y envía tus pull requests.

## Licencia

Este proyecto está bajo la Licencia MIT.
