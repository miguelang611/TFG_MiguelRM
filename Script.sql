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
IMG VARCHAR(100),
FOREIGN KEY(EMAIL) REFERENCES USUARIOS(EMAIL),
FOREIGN KEY(ID_CATEGORIA) REFERENCES CATEGORIAS(ID_CATEGORIA)
);


INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('1', 'Deportivos', 'Eventos relacionados con veh??culos deportivos');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('2', 'Salones', 'Eventos relacionados con los salones del autom??vil');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('3', 'Cl??sicos', 'Eventos relacionados con los cl??sicos');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('4', 'Circuito', 'Eventos relacionados con tandas en circuitos');
INSERT INTO `TFG_MiguelRM`.`tipos` (`ID_TIPO`, `NOMBRE`, `DESCRIPCION`) VALUES ('5', 'Monomarca', 'Eventos relacionados con una marca en concreto');

INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `IMG`, `DESCRIPCION`,  `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('1', 'ReproBeasts VIII', '/resources/media/eventos/1.jpg', '8?? Edici??n de la competici??n de tandas de los 50 coches m??s potenciados de Espa??a', '2021-06-20', '12', 'Circuito del Jarama - Madrid', 'activo', 's', '100', '50', '50', '4');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `IMG`, `DESCRIPCION`,  `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('2', 'Sal??n de Ginebra 2022', '/resources/media/eventos/2.jpg', 'Primera edici??n post-covid del sal??n mundial del autom??vil de m??s importancia a nivel mundial', '2022-01-20', '4', 'Suiza', 'activo', 's', '10000', '2000', '400', '2');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `IMG`, `DESCRIPCION`,  `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('3', 'Autobello Toledo', '/resources/media/eventos/3.jpg', 'Concentraci??n de superdeportivos: Ferrari, Lamborghini, Bugatti... Y mucho m??s!', '2021-07-01', '3', 'Toledo', 'cancelado', '', '150', '50', '70', '1');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `IMG`, `DESCRIPCION`,  `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('4', 'Classic Rules', '/resources/media/eventos/4.jpg', 'Concentraci??n de veh??culos cl??sicos (+25 a??os) en el circuito de Barcelona. Se incluye la posibilidad de 3 vueltas al circuito bajo pago de importe extra in situ', '2021-05-29', '2', 'Barcelona', 'activo', '', '300', '100', '40', '3');
INSERT INTO `TFG_MiguelRM`.`eventos` (`ID_EVENTO`, `NOMBRE`, `IMG`, `DESCRIPCION`,  `FECHA_INICIO`, `DURACION`, `DIRECCION`, `ESTADO`, `DESTACADO`, `AFORO_MAXIMO`, `MINIMO_ASISTENCIA`, `PRECIO`, `ID_TIPO`) VALUES ('5', 'AudiSportIberica 2021', '/resources/media/eventos/5.jpg', 'Concentraci??n monomarca de veh??culos Audi en Albacete, organizada en colaboraci??n con el club AudiSportIberica', '2021-07-15', '12', 'Barcelona', 'activo', '', '200', '35', '70', '5');


INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('1', 'admin');
INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('2', 'organizador');
INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('3', 'redactor');
INSERT INTO `TFG_MiguelRM`.`perfiles` (`ID_PERFIL`, `NOMBRE`) VALUES ('4', 'usuario');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('admin@admin.com', '{noop}admin', 'Administrador');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('admin@admin.com', '1');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('organizador@organizador.com', '{noop}organizador', 'Marta');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('organizador@organizador.com', '2');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('redactor@redactor.com', '{noop}redactor', 'Pepe');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('redactor@redactor.com', '3');

INSERT INTO `tfg_miguelrm`.`usuarios` (`EMAIL`, `PASSWORD`, `NOMBRE`) VALUES ('usuario@usuario.com', '{noop}usuario', 'Arturo');
INSERT INTO `tfg_miguelrm`.`usuario_perfiles` (`EMAIL`, `ID_PERFIL`) VALUES ('usuario@usuario.com', '4');

INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('1', 'General', 'Categor??a general');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('2', 'Pr??ximos Eventos', 'Lanzamientos de nuevos eventos nuevos');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('3', 'Veh??culos Nuevos', 'Lanzamientos de nuevos modelos de veh??culos nuevos');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('4', 'Pruebas', 'Pruebas de coches');
INSERT INTO `tfg_miguelrm`.`categorias` (`ID_CATEGORIA`, `NOMBRE`, `DESCRIPCION`) VALUES ('5', 'Consejos', 'Consejos para tu coche');


INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`, `IMG`) VALUES ('1', 'admin@admin.com', 'Bienvenida', '??MRM Performance!', 'El equipo de MRM Performance os damos la bienvenida a nuestra nueva web', 's', '2021-05-12', '/resources/media/noticias/1.jpg');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`, `IMG`) VALUES ('2', 'organizador@organizador.com', 'Evento Reprobeast', '??Has visto nuestro nuevo evento?', 'El d??a sabe dios cuando se produri?? el evento Reprobeast, ??quieres saber m??s? --> <a href="http://localhost:8083/eventos/detalle/1?">Click aqu??</a>', '', '2021-05-20', '/resources/media/noticias/2.jpg');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`, `IMG`) VALUES ('3', 'redactor@redactor.com', 'BMW M5 2022', 'La nueva bestia b??vara', 'Se ha presentado la nueva revisi??n del sed??n b??varo m??s brutal: el nuevo BMW M5 2022', 's', '2021-05-23', '/resources/media/noticias/3.jpg');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`, `IMG`) VALUES ('4', 'redactor@redactor.com', 'Probamos el nuevo Mercedes-Benz Clase E', 'Confort y elegancia al comp??s de la velocidad', 'Hoy probamos el nuevo Mercedes-Benz Clase E, la berlina de referencia de la firma de la estrella', 's', '2021-05-26', '/resources/media/noticias/4.jpg');
INSERT INTO `tfg_miguelrm`.`noticias` (`ID_CATEGORIA`, `EMAIL`, `NOMBRE`, `SUBTITULO`, `DETALLE`, `DESTACADA`, `FECHA`, `IMG`) VALUES ('5', 'redactor@redactor.com', '??C??mo lavar tu coche...', 'Sin dejar marcas?', 'A la hora de lavar un coche son muchas las cosas que tenemos que fijarnos: esponja, champ??, tipo de agua, balleta de secado y productos espec??ficos para las llantas', '', '2021-06-03', '/resources/media/noticias/5.jpg');