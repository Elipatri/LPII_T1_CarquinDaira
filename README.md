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
CREATE DATABASE `BD2_Carquin`;

use BD2_Carquin;

CREATE TABLE `clientes` (
  `id_cliente` int NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `peliculas` (
  `id_pelicula` int NOT NULL,
  `genero` varchar(50) NOT NULL,
  `stock` int NOT NULL,
  `titulo` varchar(100) NOT NULL,
  PRIMARY KEY (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `alquileres` (
  `id_alquiler` int NOT NULL AUTO_INCREMENT,
  `estado` enum('ACTIVO','DEVUELTO','RETRASADO') NOT NULL,
  `fecha` date NOT NULL,
  `total` double NOT NULL,
  `id_cliente` int NOT NULL,
  PRIMARY KEY (`id_alquiler`),
  KEY `FKc32vjwe393watfcig6e5udx5x` (`id_cliente`),
  CONSTRAINT `FKc32vjwe393watfcig6e5udx5x` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `detalle_alquiler` (
  `id_alquiler` int NOT NULL,
  `id_pelicula` int NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`id_alquiler`,`id_pelicula`),
  KEY `FK6059wr1fl3get229o3hoaq740` (`id_pelicula`),
  CONSTRAINT `FK2umgu6w5nl9cg9ax6nxi5bqmf` FOREIGN KEY (`id_alquiler`) REFERENCES `alquileres` (`id_alquiler`),
  CONSTRAINT `FK6059wr1fl3get229o3hoaq740` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO clientes VALUES (1,'Ana Torres','ana@mail.com'),(2,'Luis Pérez','luis@mail.com'),(3,'María Díaz','maria@mail.com');
INSERT INTO peliculas (id_pelicula, titulo, genero, stock) 
VALUES 
  (1, 'Lilo & Stich', 'Animada', 5),
  (2, 'Titanic', 'Romance', 3),
  (3, 'Matrix', 'Acción', 4);

select * from clientes c;
select * from peliculas p;
select * from alquileres a ;
select * from detalle_alquiler da ;


