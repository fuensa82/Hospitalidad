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
  `Plazas` int(11) NOT NULL,
  `Observaciones` varchar(250) DEFAULT NULL,
  `idViaje` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAutobus`),
  KEY `FK1 id Viaje` (`idViaje`),
  CONSTRAINT `FK1 id Viaje` FOREIGN KEY (`idViaje`) REFERENCES `viajes` (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Se darán de alta los autobuses. Cada Viaje tendrá sus propios autobuses, se deberán crear nuevo enla aplicación cada año';

-- La exportación de datos fue deseleccionada.

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
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='Aquí daremos de alta los datos de todas las personas que se realcionen con el viaje o que haya que controlar de alguna manera, ya sean hospitalarios, pacientes, conductores (si es que hiciera falta), ...';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.plazas
CREATE TABLE IF NOT EXISTS `plazas` (
  `idPlaza` int(11) NOT NULL AUTO_INCREMENT,
  `idAutobus` int(11) NOT NULL DEFAULT 0,
  `idPersona` int(11) DEFAULT 0,
  PRIMARY KEY (`idPlaza`,`idAutobus`),
  KEY `FK1 autobus` (`idAutobus`),
  KEY `FK2 persona` (`idPersona`),
  CONSTRAINT `FK1 autobus` FOREIGN KEY (`idAutobus`) REFERENCES `autobuses` (`idAutobus`),
  CONSTRAINT `FK2 persona` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Plazas de autobus';

-- La exportación de datos fue deseleccionada.

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

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.tiposviajeros
CREATE TABLE IF NOT EXISTS `tiposviajeros` (
  `idTipoViajero` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCortoTipo` char(15) DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idTipoViajero`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Como en los viajes hay diferentes tipos de viajeros, aquí podremos darlos de alta para poder manejarlos en el programa';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.viajes
CREATE TABLE IF NOT EXISTS `viajes` (
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `FechaIni` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Aquí iran los datos generales de cada uno de los viajes';

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
