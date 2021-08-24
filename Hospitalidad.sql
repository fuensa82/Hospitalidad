-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.5.6-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para hospitalidad
CREATE DATABASE IF NOT EXISTS `hospitalidad` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hospitalidad`;

-- Volcando estructura para tabla hospitalidad.autobuses
CREATE TABLE IF NOT EXISTS `autobuses` (
  `idAutobus` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Plazas` int(11) NOT NULL,
  `Observaciones` varchar(250) DEFAULT NULL,
  `idViaje` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAutobus`),
  KEY `FK1 id Viaje` (`idViaje`),
  CONSTRAINT `FK1 id Viaje` FOREIGN KEY (`idViaje`) REFERENCES `viajes` (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='Se darán de alta los autobuses. Cada Viaje tendrá sus propios autobuses, se deberán crear nuevo enla aplicación cada año';

-- Volcando datos para la tabla hospitalidad.autobuses: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `autobuses` DISABLE KEYS */;
INSERT IGNORE INTO `autobuses` (`idAutobus`, `Descripcion`, `Plazas`, `Observaciones`, `idViaje`) VALUES
	(3, 'Nº 1', 3, 'Vitorino 1', 1),
	(4, 'Nº 2', 6, 'Vitorino 2', 1),
	(5, 'Nº 3', 3, 'Vitorino 3', 1),
	(6, 'Nº 4', 2, 'Samar 1', 1),
	(7, 'Nº 5', 2, 'Samar 2', 1),
	(8, 'Nº 1', 3, 'Vitorino 4', 2),
	(9, 'Nº 2', 2, 'Vitorino 3', 2),
	(10, 'Nº 3', 1, 'VPT 1', 2),
	(11, 'Nº 4', 8, 'VPT 2', 2),
	(12, 'Nº 5', 2, 'Vitorino 2', 2);
/*!40000 ALTER TABLE `autobuses` ENABLE KEYS */;

-- Volcando estructura para tabla hospitalidad.personas
CREATE TABLE IF NOT EXISTS `personas` (
  `idPersona` int(11) NOT NULL AUTO_INCREMENT,
  `DNI` char(10) DEFAULT NULL,
  `Nombre` varchar(50) NOT NULL,
  `Apellidos` varchar(70) NOT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `Correo` varchar(70) DEFAULT NULL,
  `Telefono1` char(11) DEFAULT NULL,
  `Telefono2` char(11) DEFAULT NULL,
  `Direccion` varchar(50) DEFAULT NULL,
  `CP` char(5) DEFAULT NULL,
  `Localidad` varchar(50) DEFAULT NULL,
  `Provincia` varchar(50) DEFAULT NULL,
  `Observaciones` varchar(200) DEFAULT NULL,
  `Activo` binary(5) NOT NULL DEFAULT '1\0\0\0\0',
  `InformeMedico` text DEFAULT NULL,
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='Aquí daremos de alta los datos de todas las personas que se realcionen con el viaje o que haya que controlar de alguna manera, ya sean hospitalarios, pacientes, conductores (si es que hiciera falta), ...';

-- Volcando datos para la tabla hospitalidad.personas: ~11 rows (aproximadamente)
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT IGNORE INTO `personas` (`idPersona`, `DNI`, `Nombre`, `Apellidos`, `FechaNacimiento`, `Correo`, `Telefono1`, `Telefono2`, `Direccion`, `CP`, `Localidad`, `Provincia`, `Observaciones`, `Activo`, `InformeMedico`) VALUES
	(1, '03885536P', 'Victor', 'Palomo Silva', '1982-01-22', 'fuensa82@gmail.com', '646268400', '925784163', 'Calle Tales de Mileto, 3', '45111', 'Cobisa', 'Toledo', 'Es el marido de Clara', _binary 0x7472756500, 'Es un poco corto demollera'),
	(2, '03923452C', 'Clara', 'Garcia-Verdugo Arroyo', '1987-06-10', NULL, NULL, NULL, NULL, NULL, 'Cobisa', NULL, NULL, _binary 0x7472756500, NULL),
	(3, '12345678F', 'Arturo', 'Lopez Perez', '1996-08-06', NULL, NULL, NULL, NULL, NULL, 'Fuensalida', NULL, NULL, _binary 0x7472756500, NULL),
	(4, '12342334P', 'Gabriel', 'Sanchez Tenonio', '1999-07-29', 'gabriel@gmail.com', '654', '925', '', '45510', 'Fuensalida', 'Toledo', 'adasd', _binary 0x7472756500, 'asddd nmedico'),
	(5, '00000001P', 'Carla', 'Roson', '1982-01-22', NULL, NULL, NULL, NULL, NULL, 'Fuensalida', NULL, NULL, _binary 0x7472756500, NULL),
	(6, '00000002P', 'Mencia', 'Blanco', '1987-06-10', NULL, NULL, NULL, NULL, NULL, 'Cobisa', NULL, NULL, _binary 0x7472756500, NULL),
	(7, '00000003P', 'Polo', 'Benavent', '1996-08-06', NULL, NULL, NULL, NULL, NULL, 'Fuensalida', NULL, NULL, _binary 0x7472756500, NULL),
	(8, '00000004P', 'Samuel', 'Sanchez Torres', '1999-07-29', '', '', '', '', '', 'Toledo', '', '', _binary 0x7472756500, ''),
	(9, '00000005P', 'Valerio', 'Montesinos', '1999-07-29', NULL, NULL, NULL, NULL, NULL, 'Toledo', NULL, NULL, _binary 0x7472756500, NULL),
	(10, '00000006P', 'Ander', 'Muñoz', '1999-07-29', '', '', '', '', '', 'Toledo', '', '', _binary 0x7472756500, ''),
	(11, '00000007P', 'Ari', 'Martin', '1999-07-29', NULL, NULL, NULL, NULL, NULL, 'Toledo', NULL, NULL, _binary 0x7472756500, NULL);
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;

-- Volcando estructura para tabla hospitalidad.relpersonaautobus
CREATE TABLE IF NOT EXISTS `relpersonaautobus` (
  `idAutobus` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`idAutobus`,`idPersona`),
  KEY `FK2 persona` (`idPersona`),
  CONSTRAINT `FK1 autobus` FOREIGN KEY (`idAutobus`) REFERENCES `autobuses` (`idAutobus`),
  CONSTRAINT `FK2 persona` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla hospitalidad.relpersonaautobus: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `relpersonaautobus` DISABLE KEYS */;
INSERT IGNORE INTO `relpersonaautobus` (`idAutobus`, `idPersona`) VALUES
	(9, 2),
	(9, 6),
	(11, 1),
	(11, 3),
	(11, 4),
	(11, 7),
	(11, 8),
	(11, 9),
	(11, 10),
	(11, 11);
/*!40000 ALTER TABLE `relpersonaautobus` ENABLE KEYS */;

-- Volcando estructura para tabla hospitalidad.relviajetodo
CREATE TABLE IF NOT EXISTS `relviajetodo` (
  `idViaje` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  `idTipoViajero` int(11) NOT NULL,
  PRIMARY KEY (`idViaje`,`idPersona`),
  KEY `FK_relviajetodo_personas` (`idPersona`),
  KEY `FK_relviajetodo_tiposviajeros` (`idTipoViajero`),
  CONSTRAINT `FK_relviajetodo_personas` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`),
  CONSTRAINT `FK_relviajetodo_tiposviajeros` FOREIGN KEY (`idTipoViajero`) REFERENCES `tiposviajeros` (`idTipoViajero`),
  CONSTRAINT `FK_relviajetodo_viajes` FOREIGN KEY (`idViaje`) REFERENCES `viajes` (`idViaje`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Esta es la tabla que contiene todas las relaciones, viaje, perosonas, cama, asiento ... es toda la información de una persona en un viaje en concreto';

-- Volcando datos para la tabla hospitalidad.relviajetodo: ~11 rows (aproximadamente)
/*!40000 ALTER TABLE `relviajetodo` DISABLE KEYS */;
INSERT IGNORE INTO `relviajetodo` (`idViaje`, `idPersona`, `idTipoViajero`) VALUES
	(2, 1, 1),
	(2, 2, 1),
	(2, 3, 1),
	(2, 4, 1),
	(2, 5, 1),
	(2, 7, 1),
	(2, 8, 1),
	(2, 10, 1),
	(2, 11, 1),
	(2, 6, 2),
	(2, 9, 2);
/*!40000 ALTER TABLE `relviajetodo` ENABLE KEYS */;

-- Volcando estructura para tabla hospitalidad.tiposviajeros
CREATE TABLE IF NOT EXISTS `tiposviajeros` (
  `idTipoViajero` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCortoTipo` char(15) DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idTipoViajero`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Como en los viajes hay diferentes tipos de viajeros, aquí podremos darlos de alta para poder manejarlos en el programa';

-- Volcando datos para la tabla hospitalidad.tiposviajeros: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `tiposviajeros` DISABLE KEYS */;
INSERT IGNORE INTO `tiposviajeros` (`idTipoViajero`, `NombreCortoTipo`, `Descripcion`) VALUES
	(1, 'Hospitalario', 'Hospitalario'),
	(2, 'Enfermo', 'Enfermo');
/*!40000 ALTER TABLE `tiposviajeros` ENABLE KEYS */;

-- Volcando estructura para tabla hospitalidad.viajes
CREATE TABLE IF NOT EXISTS `viajes` (
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `FechaIni` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Aquí iran los datos generales de cada uno de los viajes';

-- Volcando datos para la tabla hospitalidad.viajes: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `viajes` DISABLE KEYS */;
INSERT IGNORE INTO `viajes` (`idViaje`, `Nombre`, `FechaIni`, `FechaFin`) VALUES
	(1, 'Peregrinación 2022', '2021-07-19', '2021-07-22'),
	(2, 'Peregrinación 2023', '2021-07-19', '2021-07-19');
/*!40000 ALTER TABLE `viajes` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
