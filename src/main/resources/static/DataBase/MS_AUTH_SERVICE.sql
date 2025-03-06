CREATE DATABASE ms_auth;
USE ms_auth;

DROP TABLE IF EXISTS tb_menu;
CREATE TABLE tb_menu (
cod_menu INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) DEFAULT NULL,
url VARCHAR(100) DEFAULT NULL,
icon VARCHAR(50) DEFAULT NULL
);

DROP TABLE IF EXISTS tb_rol;
CREATE TABLE tb_rol (
cod_rol INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
descripcion VARCHAR(45) DEFAULT NULL
);

DROP TABLE IF EXISTS tb_menu_rol;
CREATE TABLE tb_menu_rol (
cod_menu INT NOT NULL,
cod_rol INT NOT NULL,
FOREIGN KEY (cod_rol) REFERENCES tb_rol (cod_rol),
FOREIGN KEY (cod_menu) REFERENCES tb_menu (cod_menu)
);

DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
profile_image VARCHAR(500) NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
estado TINYINT(1) NOT NULL DEFAULT 1,
idrol INT NOT NULL,
FOREIGN KEY (idrol) REFERENCES tb_rol (cod_rol)
);

INSERT INTO tb_menu VALUES(1, 'Usuarios','/sistema/usuario','fa-solid fa-user');
INSERT INTO tb_menu VALUES(2, 'Vehículos','/sistema/vehiculo','fa-solid fa-car');

INSERT INTO tb_rol VALUES(1,'ADMIN');
INSERT INTO tb_rol VALUES(2,'TRABAJADOR');
INSERT INTO tb_rol VALUES(3,'CLIENTE');

INSERT INTO tb_menu_rol VALUES(1,1);
INSERT INTO tb_menu_rol VALUES(1,2);
INSERT INTO tb_menu_rol VALUES(2,1);
INSERT INTO tb_menu_rol VALUES(2,2);
INSERT INTO tb_menu_rol VALUES(2,3);

INSERT INTO tb_user (first_name, last_name, email, password, profile_image, idrol)
VALUES('Anderson Jesús', 'Aquino Pineda', 'anderjesus1208@gmail.com', '$2a$10$qnlAA4XgE20VvtMU5ISEz.DgkzqwZJXc3/bw4LnaMsLA7h3d4Zim.', 'imagen1', 1);
INSERT INTO tb_user (first_name, last_name, email, password, profile_image, idrol)
VALUES('Daniel Josué', 'Preciado Martinez', 'daniel123@gmail.com', '$2a$10$qnlAA4XgE20VvtMU5ISEz.DgkzqwZJXc3/bw4LnaMsLA7h3d4Zim.', 'imagen2', 2);
INSERT INTO tb_user (first_name, last_name, email, password, profile_image, idrol)
VALUES('Gerson Edward', 'Molle Mejía', 'gersoned@gmail.com', '$2a$10$qnlAA4XgE20VvtMU5ISEz.DgkzqwZJXc3/bw4LnaMsLA7h3d4Zim.', 'imagen3', 3);
