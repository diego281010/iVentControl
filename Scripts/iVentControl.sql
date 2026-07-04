-- =====================================================
-- CREACIÓN DE LA BASE DE DATOS (PostgreSQL Tradicional)
-- En Supabase NO se ejecuta, ya viene creada.
-- Nombre del proyecto: iVentControl

CREATE DATABASE iVentControl;

CREATE TABLE usuario(
-- =====================================================
------TABLA usuario--------
                        id_usuario SERIAL PRIMARY KEY,

                        nombre VARCHAR(50) NOT NULL,

                        apellido VARCHAR(50) NOT NULL,

                        usuario VARCHAR(30) NOT NULL UNIQUE,

                        correo VARCHAR(100) NOT NULL UNIQUE,

                        contrasena VARCHAR(255) NOT NULL,

                        rol VARCHAR(20) NOT NULL,

                        estado VARCHAR(20) NOT NULL

);
--------TABLA proveedor----------
CREATE TABLE proveedor(

                          id_proveedor SERIAL PRIMARY KEY,

                          empresa VARCHAR(100) NOT NULL,

                          telefono VARCHAR(20),

                          correo VARCHAR(100),

                          direccion VARCHAR(150),

                          pais VARCHAR(50)

);
---------INFORMACIÓN proveedor-----------
INSERT INTO proveedor
(empresa, telefono, correo, direccion, pais)
VALUES

    ('Apple Inc.','+1 800 275 2273','contact@apple.com','Cupertino, California','Estados Unidos'),

    ('iStore Ecuador','02-600-4500','ventas@istore.ec','Quito','Ecuador'),

    ('Mac Center Ecuador','02-395-6000','info@maccenter.ec','Quito','Ecuador'),

    ('TecnoApple','04-380-4500','contacto@tecnoapple.ec','Guayaquil','Ecuador'),

    ('Importadora Digital','02-245-8000','ventas@importadora.ec','Quito','Ecuador');


----------TABLA producto----------
CREATE TABLE producto(

                         id_producto SERIAL PRIMARY KEY,

                         nombre_producto VARCHAR(100) NOT NULL,

                         tipo VARCHAR(40) NOT NULL,

                         color VARCHAR(30),

                         almacenamiento VARCHAR(20),

                         precio_compra DECIMAL(10,2) NOT NULL,

                         precio_venta DECIMAL(10,2) NOT NULL,

                         stock INTEGER NOT NULL,

                         estado VARCHAR(30) NOT NULL,

                         id_proveedor INTEGER NOT NULL,

                         FOREIGN KEY(id_proveedor)
                             REFERENCES proveedor(id_proveedor)

);


INSERT INTO producto
(nombre_producto,tipo,color,almacenamiento,precio_compra,precio_venta,stock,estado,id_proveedor)
-------INFORMACIÓN productos--------
VALUES

    ('iPhone 16','iPhone','Negro','128 GB',780.00,999.00,18,'Disponible',1),

    ('iPhone 16 Plus','iPhone','Azul','256 GB',890.00,1149.00,12,'Disponible',1),

    ('iPhone 16 Pro','iPhone','Titanio Negro','256 GB',990.00,1299.00,10,'Disponible',1),

    ('iPhone 16 Pro Max','iPhone','Titanio Natural','512 GB',1120.00,1499.00,8,'Disponible',1),

    ('iPhone 15','iPhone','Rosa','128 GB',680.00,899.00,20,'Disponible',2),

    ('MacBook Air M4 13"','MacBook','Plata','256 GB',980.00,1249.00,9,'Disponible',2),

    ('MacBook Air M4 15"','MacBook','Medianoche','512 GB',1200.00,1499.00,7,'Disponible',2),

    ('MacBook Pro M4','MacBook','Negro Espacial','512 GB',1650.00,1999.00,5,'Disponible',1),

    ('Mac Mini M4','Mac','Plata','256 GB',560.00,699.00,14,'Disponible',3),

    ('iMac M4 24"','iMac','Azul','512 GB',1350.00,1699.00,6,'Disponible',3),

    ('iPad 11','iPad','Plata','128 GB',360.00,499.00,15,'Disponible',2),

    ('iPad Air M3','iPad','Morado','256 GB',590.00,799.00,10,'Disponible',2),

    ('iPad Pro M4 11"','iPad','Negro','256 GB',830.00,1099.00,8,'Disponible',1),

    ('Apple Watch SE','Apple Watch','Blanco',NULL,180.00,249.00,22,'Disponible',4),

    ('Apple Watch Series 10','Apple Watch','Negro',NULL,330.00,449.00,16,'Disponible',4),

    ('Apple Watch Ultra 2','Apple Watch','Titanio',NULL,610.00,799.00,5,'Disponible',1),

    ('AirPods 4','AirPods','Blanco',NULL,90.00,129.00,25,'Disponible',5),

    ('AirPods Pro 2','AirPods','Blanco',NULL,180.00,249.00,18,'Disponible',5),

    ('AirPods Max','AirPods','Azul',NULL,430.00,549.00,7,'Disponible',5),

    ('Apple Pencil USB-C','Accesorio','Blanco',NULL,55.00,79.00,30,'Disponible',3),

    ('Apple Pencil Pro','Accesorio','Blanco',NULL,95.00,129.00,15,'Disponible',3),

    ('Magic Mouse','Accesorio','Blanco',NULL,55.00,79.00,20,'Disponible',4),

    ('Magic Keyboard','Accesorio','Blanco',NULL,120.00,149.00,12,'Disponible',4),

    ('Cargador USB-C 20W','Accesorio','Blanco',NULL,18.00,29.00,40,'Disponible',5),

    ('HomePod Mini','HomePod','Gris',NULL,75.00,99.00,11,'Disponible',2);
----INSERT Administradores----
INSERT INTO usuario (nombre, apellido, usuario, correo, contrasena, rol, estado)
VALUES ('Diego', 'Almeida', 'dalmeida', 'diego@gmail.com', '#ProyectoPoo', 'Administrador', 'Activo');

INSERT INTO usuario (nombre, apellido, usuario, correo, contrasena, rol, estado)
VALUES ('Jordy', 'Cajas', 'jcajas', 'jordy@gmail.com', '#ProyectoPoo', 'Vendedor', 'Activo');
