
SELECT personas.Nombre, personas.Apellidos, personas.Localidad, autobuses.Descripcion AS Bus, hoteles.NombreHotel AS hotel, habitaciones.Descripcion1 AS habitacion
FROM personas, habitaciones, hoteles, autobuses, relpersonaautobus, relpersonahabitacion, relviajetodo, viajes
WHERE habitaciones.idHotel=hoteles.IdHotel AND
	personas.idPersona=relviajetodo.idPersona AND
	relpersonahabitacion.idPersona=personas.idPersona AND
	relpersonahabitacion.idHabitacion=habitaciones.idHabitacion AND
	habitaciones.idHotel=hoteles.IdHotel AND
	autobuses.idAutobus=relpersonaautobus.idAutobus AND
	personas.idPersona=relpersonaautobus.idPersona and
	relviajetodo.idViaje=viajes.idViaje AND 
	relviajetodo.idViaje='1'
ORDER BY personas.Apellidos, personas.Nombre