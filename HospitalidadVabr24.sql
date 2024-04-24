-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.11.6-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando estructura para tabla hospitalidad.autobuses
CREATE TABLE IF NOT EXISTS `autobuses` (
  `idAutobus` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(50) DEFAULT NULL,
  `PlazasNoEnfermos` int(11) NOT NULL COMMENT 'Plazas que irán destinadas a personas no enfermas',
  `PlazasEnfermos` int(11) DEFAULT NULL COMMENT 'Plazas que queremos asignar como máximo para enfermos. Estas plazas solo se podrán ocupar con enfermos y las que queden libres hasta completar las plazas totales serán las que se llenarán con hospitalarios.',
  `Observaciones` varchar(250) DEFAULT NULL,
  `idViaje` int(11) DEFAULT NULL,
  PRIMARY KEY (`idAutobus`),
  KEY `FK1 id Viaje` (`idViaje`),
  CONSTRAINT `FK1 id Viaje` FOREIGN KEY (`idViaje`) REFERENCES `viajes` (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Se darán de alta los autobuses. Cada Viaje tendrá sus propios autobuses, se deberán crear nuevo enla aplicación cada año';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.habitaciones
CREATE TABLE IF NOT EXISTS `habitaciones` (
  `idHabitacion` int(11) NOT NULL AUTO_INCREMENT,
  `Camas` int(11) NOT NULL,
  `Descripcion1` varchar(250) DEFAULT NULL,
  `Descripcion2` varchar(250) DEFAULT NULL,
  `Observaciones` varchar(250) DEFAULT NULL,
  `idViaje` int(11) NOT NULL,
  `idHotel` int(11) NOT NULL,
  PRIMARY KEY (`idHabitacion`),
  KEY `FK1 viaje` (`idViaje`),
  KEY `FK2 hotel` (`idHotel`),
  CONSTRAINT `FK1 viaje` FOREIGN KEY (`idViaje`) REFERENCES `viajes` (`idViaje`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK2 hotel` FOREIGN KEY (`idHotel`) REFERENCES `hoteles` (`IdHotel`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Aquí se añadiran todas las habitaciones sin importar hotel u hospital en el que se encuentre. Al final cada habitación solo será de una peregrinación y dará igual en qué hotel esté.';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.hoteles
CREATE TABLE IF NOT EXISTS `hoteles` (
  `IdHotel` int(11) NOT NULL AUTO_INCREMENT,
  `NombreHotel` varchar(50) NOT NULL,
  `Direccion` varchar(50) DEFAULT NULL,
  `Telefono` varchar(13) DEFAULT NULL,
  `Correo` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`IdHotel`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

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
  `Activo` binary(5) DEFAULT 'true\0',
  `InformeMedico` text DEFAULT NULL,
  `ActualTipoViajero` int(11) NOT NULL DEFAULT 0 COMMENT 'Guardamos aquí el equipo al que pertenece actualmente la persona',
  `ComoHospitalarioAnteriorA2022` int(11) NOT NULL DEFAULT 0 COMMENT 'Sirve para apuntar el número de viajes como hospitalario anteriores a 2022. Desde 2022 ya se cuentan solos\r\n',
  `ComoPeregrino` int(11) NOT NULL DEFAULT 0 COMMENT 'Aquí habrá que ir marcando los años que ha ido alguien de peregrino. Como este dato no se carga automaticamente en el porograma habrá que ir actualizandolo a mano',
  PRIMARY KEY (`idPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=4277 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Aquí daremos de alta los datos de todas las personas que se realcionen con el viaje o que haya que controlar de alguna manera, ya sean hospitalarios, pacientes, conductores (si es que hiciera falta), ...';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.relpersonaautobus
CREATE TABLE IF NOT EXISTS `relpersonaautobus` (
  `idAutobus` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`idAutobus`,`idPersona`),
  KEY `FK2 persona` (`idPersona`),
  CONSTRAINT `FK1 autobus` FOREIGN KEY (`idAutobus`) REFERENCES `autobuses` (`idAutobus`),
  CONSTRAINT `FK2 persona` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.relpersonahabitacion
CREATE TABLE IF NOT EXISTS `relpersonahabitacion` (
  `idHabitacion` int(11) NOT NULL,
  `idPersona` int(11) NOT NULL,
  PRIMARY KEY (`idHabitacion`,`idPersona`),
  KEY `FK1 persona` (`idPersona`),
  CONSTRAINT `FK1 persona` FOREIGN KEY (`idPersona`) REFERENCES `personas` (`idPersona`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK2 habitacion` FOREIGN KEY (`idHabitacion`) REFERENCES `habitaciones` (`idHabitacion`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Relaciona las habitaciones y las personas que van en la habitacion';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Esta es la tabla que contiene todas las relaciones, viaje, perosonas, cama, asiento ... es toda la información de una persona en un viaje en concreto';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla hospitalidad.tiposviajeros
CREATE TABLE IF NOT EXISTS `tiposviajeros` (
  `idTipoViajero` int(11) NOT NULL AUTO_INCREMENT,
  `NombreCortoTipo` char(15) DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idTipoViajero`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Como en los viajes hay diferentes tipos de viajeros, aquí podremos darlos de alta para poder manejarlos en el programa';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para vista hospitalidad.viajecompleto
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `viajecompleto` (
	`Nombre` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
	`Apellidos` VARCHAR(70) NOT NULL COLLATE 'utf8mb3_general_ci',
	`Localidad` VARCHAR(50) NULL COLLATE 'utf8mb3_general_ci',
	`Bus` VARCHAR(50) NULL COLLATE 'utf8mb3_general_ci',
	`hotel` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
	`habitacion` VARCHAR(250) NULL COLLATE 'utf8mb3_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para tabla hospitalidad.viajes
CREATE TABLE IF NOT EXISTS `viajes` (
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `FechaIni` date DEFAULT NULL,
  `FechaFin` date DEFAULT NULL,
  PRIMARY KEY (`idViaje`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='Aquí iran los datos generales de cada uno de los viajes';

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para vista hospitalidad.viajecompleto
-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `viajecompleto`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `viajecompleto` AS SELECT personas.Nombre, personas.Apellidos, personas.Localidad, autobuses.Descripcion AS Bus, hoteles.NombreHotel AS hotel, habitaciones.Descripcion1 AS habitacion
FROM personas, habitaciones, hoteles, autobuses, relpersonaautobus, relpersonahabitacion, relviajetodo
WHERE habitaciones.idHotel=hoteles.IdHotel AND
	personas.idPersona=relviajetodo.idPersona AND
	relpersonahabitacion.idPersona=personas.idPersona AND
	relpersonahabitacion.idHabitacion=habitaciones.idHabitacion AND
	habitaciones.idHotel=hoteles.IdHotel AND
	autobuses.idAutobus=relpersonaautobus.idAutobus AND
	personas.idPersona=relpersonaautobus.idPersona -- and
	-- habitaciones.idViaje='1'
ORDER BY personas.Apellidos, personas.Nombre ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
