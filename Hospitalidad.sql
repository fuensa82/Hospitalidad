-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.6.5-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para hospitalidad
CREATE DATABASE IF NOT EXISTS `hospitalidad` /*!40100 DEFAULT CHARACTER SET utf8mb3 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3 COMMENT='Se darán de alta los autobuses. Cada Viaje tendrá sus propios autobuses, se deberán crear nuevo enla aplicación cada año';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.habitaciones
CREATE TABLE IF NOT EXISTS `habitaciones` (
  `idHabitacion` int(11) NOT NULL AUTO_INCREMENT,
  `Camas` int(11) NOT NULL,
  `Descripcion1` varchar(250) DEFAULT NULL,
  `Descripcion2` varchar(250) DEFAULT NULL,
  `Observaciones` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idHabitacion`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='Aquí se añadiran todas las habitaciones sin importar hotel u hospital en el que se encuentre. Al final cada habitación solo será de una peregrinación y dará igual en qué hotel esté.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.informespdf
CREATE TABLE IF NOT EXISTS `informespdf` (
  `idInformePDF` int(11) NOT NULL AUTO_INCREMENT,
  `idPersona` int(11) NOT NULL DEFAULT 0,
  `Observaciones` varchar(50) DEFAULT NULL,
  `fechaAlta` date NOT NULL,
  `fichero` longblob NOT NULL DEFAULT '',
  `extension` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idInformePDF`),
  KEY `FK_informespdf_personas` (`idPersona`),
  CONSTRAINT `FK_informespdf_personas` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

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
  `InformeMedico` text DEFAULT NULL,
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='Aquí daremos de alta los datos de todas las personas que se realcionen con el viaje o que haya que controlar de alguna manera, ya sean hospitalarios, pacientes, conductores (si es que hiciera falta), ...';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.relpersonaautobus
CREATE TABLE IF NOT EXISTS `relpersonaautobus` (
  `idAutobus` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`idAutobus`,`idPersona`),
  KEY `FK2 persona` (`idPersona`),
  CONSTRAINT `FK1 autobus` FOREIGN KEY (`idAutobus`) REFERENCES `autobuses` (`idAutobus`),
  CONSTRAINT `FK2 persona` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.relpersonahabitacion
CREATE TABLE IF NOT EXISTS `relpersonahabitacion` (
  `idHabitacion` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`idHabitacion`,`idPersona`),
  KEY `FK1 persona` (`idPersona`),
  CONSTRAINT `FK1 persona` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK2 habitacion` FOREIGN KEY (`idHabitacion`) REFERENCES `habitaciones` (`idHabitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Relaciona las habitaciones y las personas que van en la habitacion';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Esta es la tabla que contiene todas las relaciones, viaje, perosonas, cama, asiento ... es toda la información de una persona en un viaje en concreto';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.tiposviajeros
CREATE TABLE IF NOT EXISTS `tiposviajeros` (
  `idTipoViajero` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCortoTipo` char(15) DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idTipoViajero`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='Como en los viajes hay diferentes tipos de viajeros, aquí podremos darlos de alta para poder manejarlos en el programa';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.viajes
CREATE TABLE IF NOT EXISTS `viajes` (
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `FechaIni` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='Aquí iran los datos generales de cada uno de los viajes';

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
