MODELO DE EXAMEN · PROGRAMACIÓN II
Sistema de Gestión
de Base Militar
Consigna completa y aclaraciones posteriores del profesor
Objetivo: desarrollar una aplicación Java que permita administrar soldados, oficiales, cuarteles y sus relaciones desde un menú controlado por un administrativo.

 
1. Consigna general
Desarrollar una aplicación en Java para gestionar la información de una base militar, incluyendo soldados, oficiales y alojamientos. El sistema debe permitir realizar operaciones CRUD sobre cada entidad principal y gestionar sus relaciones.
2. Objetos del sistema
Objeto	Atributos
Soldado	ID, DNI, nombre y apellido.
Oficial	ID, DNI, nombre y apellido.
Cuartel	Código y capacidad.
Reserva	ID del soldado y código del cuartel.
Asignación	ID del oficial y código del cuartel.

3. Relaciones
3.1 Cuarteles y soldados
Relación de uno a muchos:
•	Un cuartel puede alojar a varios soldados.
•	Un soldado puede estar asignado a un cuartel.
•	La relación se registra mediante Reserva.
3.2 Oficiales y cuarteles
Relación de uno a uno:
•	Un oficial puede estar asignado a un cuartel.
•	Un cuartel puede tener un oficial asignado.
•	La relación se registra mediante Asignación.
4. Menú principal
Se debe crear un menú principal donde un administrativo tenga control absoluto sobre el sistema. El administrativo deberá poder:
•	Ingresar datos de soldados, oficiales y cuarteles.
•	Modificar datos de soldados, oficiales y cuarteles.
•	Consultar datos de soldados, oficiales y cuarteles.
•	Eliminar datos de soldados, oficiales y cuarteles.
•	Asignar un soldado a un cuartel.
•	Asignar un oficial a un cuartel.
5. Consultas obligatorias
•	El nombre del soldado con su cuartel asignado.
•	Los soldados supervisados por un oficial.
•	El oficial asignado a un soldado.
Menús específicos: cada entidad principal debe tener su propio menú con opciones CRUD y funcionalidades relacionadas.

6. Aclaraciones posteriores del profesor
•	Los ID de soldados y oficiales deben generarse automáticamente.
•	El código del cuartel debe generarse automáticamente.
•	El DNI, nombre, apellido y capacidad se ingresan manualmente.
•	El DNI no es lo mismo que el ID generado por el sistema.
Importante: Reserva y Asignación utilizan los ID y códigos ya generados para relacionar las entidades; no generan identificadores nuevos.

