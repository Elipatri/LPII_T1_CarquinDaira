# 🎬 Proyecto de Evaluación - Blockbuster Fake

**Sistema de alquiler de películas** desarrollado como parte de la modernización de la empresa ficticia **Blockbuster Fake**, migrando una aplicación de escritorio (Swing + JPA puro) a una solución web basada en **Spring Boot 3**, **Spring Data JPA** y **Thymeleaf**.

---

## 🧱 Tecnologías Utilizadas

- Java 21
- Spring Boot 3.5.2
- Spring Data JPA
- Spring Web
- Spring DevTools
- Thymeleaf
- Lombok
- MySQL 8

---

## 📦 Estructura del Proyecto

- `model`: Entidades JPA como `Cliente`, `Pelicula`, `Alquiler` y `DetalleAlquiler`.
- `repository`: Repositorios Spring Data JPA.
- `service`: Lógica de negocio centralizada.
- `controller`: Controladores para vistas Thymeleaf.
- `templates`: Archivos `.html` con formularios y vistas.
- `resources/application.properties`: Configuración de conexión a BD y puertos.

---

## ⚙️ Configuración

### Base de Datos

Nombre: `BD2_Carquin`  
Motor: MySQL  
Puerto: `3306`

Asegúrate de tener la base creada y ejecutar el siguiente script:

```sql
-- Script base de datos
CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

INSERT INTO cliente (nombre, email) VALUES
('Juan Pérez', 'juan@email.com'),
('Ana Torres', 'ana@email.com'),
('Luis Gutiérrez', 'luis@email.com');

CREATE TABLE pelicula (
    id_pelicula INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    stock INT NOT NULL
);

INSERT INTO pelicula (titulo, genero, stock) VALUES
('Matrix', 'Ciencia Ficción', 10),
('Titanic', 'Romance', 5),
('Inception', 'Acción', 8);

CREATE TABLE alquiler (
    id_alquiler INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    id_cliente INT,
    total DECIMAL(10,2),
    estado VARCHAR(20),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE detalle_alquiler (
    id_alquiler INT,
    id_pelicula INT,
    cantidad INT,
    PRIMARY KEY (id_alquiler, id_pelicula),
    FOREIGN KEY (id_alquiler) REFERENCES alquiler(id_alquiler),
    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id_pelicula)
);
