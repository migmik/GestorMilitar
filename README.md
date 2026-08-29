# Gestion Militar

Sistema de gestion militar desarrollado en Java para administrar soldados,
oficiales, cuarteles y las relaciones entre ellos mediante reservas y
asignaciones.

El proyecto fue construido como aplicacion de consola para Programacion II y
usa MySQL como motor de persistencia.

## Funcionalidades

- Gestion CRUD de soldados.
- Gestion CRUD de oficiales.
- Gestion CRUD de cuarteles.
- Reserva de soldados en cuarteles.
- Asignacion de oficiales a cuarteles.
- Validacion de capacidad disponible en cuarteles.
- Validacion de DNI, datos obligatorios y registros duplicados.
- Consultas especificas:
  - soldado con su cuartel asignado;
  - soldados supervisados por un oficial;
  - oficial asignado a un soldado.

## Tecnologias

- Java 21
- MySQL
- JDBC
- MySQL Connector/J
- Apache Ant / NetBeans

## Estructura del proyecto

```text
.
|-- config/
|   `-- db.example.properties
|-- src/gestion/militar/
|   |-- BaseDeDatos/
|   |-- Controladores/
|   |-- DAOS/
|   |-- Enums/
|   |-- Excepciones/
|   |-- Main/
|   |-- Modelos/
|   |-- Repositorios/
|   `-- Vistas/
|-- nbproject/
|-- build.xml
|-- consigna.md
|-- LICENSE
`-- README.md
```

## Arquitectura

El codigo esta separado por responsabilidades:

- `Modelos`: entidades principales del dominio, como `Soldado`, `Oficial`,
  `Cuartel`, `Reserva` y `Asignacion`.
- `DAOS`: acceso a datos mediante JDBC.
- `Repositorios`: contratos para operaciones especificas de reservas y
  asignaciones.
- `Controladores`: reglas de negocio y coordinacion entre vistas, modelos y
  persistencia.
- `Vistas`: menus de consola para interactuar con el sistema.
- `BaseDeDatos`: configuracion y apertura de conexion a MySQL.
- `Excepciones`: errores propios del dominio, como entidades duplicadas,
  entidades no encontradas o capacidad excedida.

## Requisitos

Antes de ejecutar el proyecto necesitas tener instalado:

- JDK 21 o superior.
- MySQL Server.
- MySQL Connector/J.
- NetBeans o Apache Ant para compilar y ejecutar el proyecto.

> Nota: el archivo `nbproject/project.properties` puede tener una ruta local al
> `.jar` de MySQL Connector/J. Si el proyecto se abre en otra computadora,
> actualizar esa referencia desde las propiedades del proyecto o agregar el
> conector al classpath.

## Configuracion de la base de datos

El proyecto espera un archivo local llamado:

```text
config/db.properties
```

Ese archivo no se sube al repositorio porque contiene credenciales locales.
Para crearlo, copia el archivo de ejemplo:

```powershell
Copy-Item config/db.example.properties config/db.properties
```

Luego edita `config/db.properties` con tus datos reales:

```properties
db.url=jdbc:mysql://localhost:3306/gestionmilitardb
db.user=tu_usuario
db.password=tu_password
```

## Script SQL sugerido

Si todavia no tenes creada la base de datos, este esquema cubre las tablas y
columnas que usa la aplicacion:

```sql
CREATE DATABASE IF NOT EXISTS gestionmilitardb;
USE gestionmilitardb;

CREATE TABLE soldados (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

CREATE TABLE oficiales (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

CREATE TABLE cuarteles (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    capacidad INT NOT NULL
);

CREATE TABLE reservas (
    soldados_codigo INT PRIMARY KEY,
    cuarteles_codigo INT NOT NULL,
    CONSTRAINT fk_reservas_soldados
        FOREIGN KEY (soldados_codigo) REFERENCES soldados(codigo),
    CONSTRAINT fk_reservas_cuarteles
        FOREIGN KEY (cuarteles_codigo) REFERENCES cuarteles(codigo)
);

CREATE TABLE asignaciones (
    oficiales_codigo INT PRIMARY KEY,
    cuarteles_codigo INT NOT NULL UNIQUE,
    CONSTRAINT fk_asignaciones_oficiales
        FOREIGN KEY (oficiales_codigo) REFERENCES oficiales(codigo),
    CONSTRAINT fk_asignaciones_cuarteles
        FOREIGN KEY (cuarteles_codigo) REFERENCES cuarteles(codigo)
);
```

## Ejecucion

### Opcion 1: desde NetBeans

1. Abrir el proyecto en NetBeans.
2. Verificar que el JDK configurado sea Java 21 o superior.
3. Agregar MySQL Connector/J a las librerias del proyecto si fuera necesario.
4. Crear y completar `config/db.properties`.
5. Ejecutar la clase principal:

```text
gestion.militar.Main.Main
```

### Opcion 2: desde consola con Ant

Desde la raiz del proyecto:

```powershell
ant clean
ant run
```

## Uso general

Al iniciar la aplicacion se muestra un menu principal desde el que se accede a:

- gestion de soldados;
- gestion de oficiales;
- gestion de cuarteles;
- gestion de reservas;
- gestion de asignaciones;
- consultas generales.

Las reservas relacionan soldados con cuarteles. Las asignaciones relacionan
oficiales con cuarteles.

## Reglas principales del sistema

- Un soldado puede tener una sola reserva activa.
- Un cuartel puede alojar varios soldados hasta alcanzar su capacidad.
- Un oficial puede tener una sola asignacion activa.
- Un cuartel puede tener un solo oficial asignado.
- No se permite reducir la capacidad de un cuartel por debajo de la cantidad de
  soldados reservados actualmente.
- El DNI debe contener entre 7 y 8 digitos numericos.

## Licencia

Este proyecto esta publicado bajo licencia MIT. Ver el archivo `LICENSE`.
