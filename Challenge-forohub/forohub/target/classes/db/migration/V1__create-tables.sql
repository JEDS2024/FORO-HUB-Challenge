CREATE TABLE cursos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE topicos (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    status VARCHAR(255) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);
