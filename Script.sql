drop database TFG_MiguelRM;
drop user miguelTFG;

CREATE DATABASE TFG_MiguelRM;
USE TFG_MiguelRM;

create user miguelTFG identified by 'miguel';
grant all privileges on TFG_MiguelRM.* to miguelTFG;


CREATE TABLE TIPOS
(ID_TIPO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(45) NOT NULL,
DESCRIPCION VARCHAR(200)
);

CREATE TABLE USUARIOS
(EMAIL VARCHAR(100) NOT NULL PRIMARY KEY,
PASSWORD VARCHAR(45) NOT NULL,
NOMBRE VARCHAR(50),
DIRECCION VARCHAR(100),
ENABLED INT NOT NULL DEFAULT 1,
FECHA_REGISTRO DATE
);

CREATE TABLE PERFILES
(ID_PERFIL INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(45) NOT NULL
);

CREATE TABLE USUARIO_PERFILES
(EMAIL VARCHAR(100) NOT NULL,
ID_PERFIL INT NOT NULL,
PRIMARY KEY(EMAIL, ID_PERFIL),
FOREIGN KEY(EMAIL) REFERENCES USUARIOS(EMAIL),
FOREIGN KEY(ID_PERFIL) REFERENCES PERFILES(ID_PERFIL)
);

CREATE TABLE EVENTOS
(ID_EVENTO INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(50) NOT NULL,
DESCRIPCION VARCHAR(200),
IMG VARCHAR(100),
FECHA_INICIO DATE,
DURACION INT,
DIRECCION VARCHAR(100),
ESTADO VARCHAR(20),
DESTACADO CHAR(1),
AFORO_MAXIMO INT,
MINIMO_ASISTENCIA INT,
PRECIO DEC(9,2),
ID_TIPO INT NOT NULL,
FOREIGN KEY(ID_TIPO) REFERENCES TIPOS(ID_TIPO)
);

CREATE TABLE RESERVAS
(ID_RESERVA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
ID_EVENTO INT NOT NULL,
EMAIL VARCHAR(100) NOT NULL,
PRECIO_VENTA DEC(9,2),
OBSERVACIONES VARCHAR(200),
CANTIDAD INT,
FOREIGN KEY(EMAIL) REFERENCES USUARIOS(EMAIL),
FOREIGN KEY(ID_EVENTO) REFERENCES EVENTOS(ID_EVENTO)
);

CREATE TABLE CATEGORIAS
(ID_CATEGORIA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(45) NOT NULL,
DESCRIPCION VARCHAR(200)
);

CREATE TABLE NOTICIAS
(ID_NOTICIA INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
ID_CATEGORIA INT(11) NOT NULL,
EMAIL VARCHAR(100) NOT NULL,
NOMBRE VARCHAR(50) NOT NULL,
SUBTITULO VARCHAR(50),
DETALLE VARCHAR(200),
DESTACADA CHAR(1),
FECHA DATE,
FOREIGN KEY(EMAIL) REFERENCES USUARIOS(EMAIL),
FOREIGN KEY(ID_CATEGORIA) REFERENCES CATEGORIAS(ID_CATEGORIA)
);


INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('1', 'Motor', 'Eventos relacionados con el mundo del motor: coches, motos, etc');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('2', 'Música', 'Eventos relacionados con conciertos, actuaciones, etc');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('3', 'Moda', 'Eventos relacionados con pasarelas, productos de cuidado personal');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('4', 'Deporte', 'Eventos relacionados con el mundo del deporte: fútbol, baloncesto, voleibol, etc');

INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `IMG`, `DESCRIPCION`,  `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('1', 'ReproBeasts VIII', '/resources/media/eventos/1.jpg', noticias'8º Edición de la competición de Drag Races de los 50 coches más potenciados de España', '2021-05-20', '12', 'Calle A - Madrid', 'activo', 's', '300', '50', '100', '1');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `DESCRIPCION`, `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('2', 'Lola Índigo', 'Concierto de Lola Índigo con su último éxito', '2021-06-25', '4', 'Zaragoza', 'activo', 's', '3000', '500', '60', '2');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `DESCRIPCION`, `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('3', 'Fashion Barcelona', 'Desfile Moda Temporada 2021 Ed.Verano', '2021-07-01', '3', 'Barcelona', 'cancelado', '', '100', '50', '300', '3');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `DESCRIPCION`, `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('4', 'Final Champions', 'Real Madrid vs PSG', '2021-05-29', '2', 'Estambul', 'activo', '', '10000', '1000', '1000', '4');

INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('1', 'admin');
INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('2', 'organizador');
INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('3', 'redactor');
INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('4', 'usuario');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('admin@admin.com', '{noop}admin', 'admin');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('admin@admin.com', '1');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('organizador@organizador.com', '{noop}organizador', 'organizador');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('organizador@organizador.com', '2');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('redactor@redactor.com', '{noop}redactor', 'redactor');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('redactor@redactor.com', '3');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('usuario@usuario.com', '{noop}usuario', 'usuario');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('usuario@usuario.com', '4');

INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('1', 'general', 'Categoría general');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('2', 'eventosnuevos', 'Lanzamientos de nuevos eventos nuevos');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('3', 'vehiculosnuevos', 'Lanzamientos de nuevos modelos de vehículos nuevos');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('4', 'pruebas', 'Pruebas de coches');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('5', 'consejos', 'Consejos para tu coche');


INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`) VALUES ('1', 'admin@admin.com', 'Buenvenida', 'Bienvenida', 'Bienvenida', 's', '2021-05-12');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`) VALUES ('2', 'organizador@organizador.com', 'Evento Reprobeast', 'Increíble', 'El día sabe dios cuando se produriá el evento Reprobeast', '', '2021-05-20');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`) VALUES ('3', 'redactor@redactor.com', 'BMW M5 2022', 'La nueva bestia bávara', 'Se ha presentado la nueva revisión del sedán bávaro más brutal: el nuevo BMW M5 2022', 's', '2021-05-23');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`) VALUES ('4', 'redactor@redactor.com', 'Probamos el nuevo Mercedes-Benz Clase E', 'Confort y elegancia al compás de la velocidad', 'Hoy probamos el nuevo Mercedes-Benz Clase E, la berlina de referencia de la firma de la estrella', 's', '2021-05-26');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`) VALUES ('5', 'redactor@redactor.com', '¿Cómo lavar tu coche...', 'Sin dejar marcas?', 'Es importante que blabla', '', '2021-06-03');