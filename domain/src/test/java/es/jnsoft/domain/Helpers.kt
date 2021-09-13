package es.jnsoft.domain

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Location

fun createFirstCurrent(): Current {
    return Current(
        clouds = 40,
        country = "ES",
        deltaTime = 1628595794000,
        humidity = 50,
        description = "nubes dispersas",
        icon = "03d",
        id = 6697298,
        location = Location(38.2324, -0.547),
        name = "Gran Alacant",
        pressure = 1016,
        rain = 0.0,
        snow = 0.0,
        sunrise = 1628745263000,
        sunset = 1628794813000,
        temp = 303.01,
        tempFeelsLike = 304.45,
        timeZone = 7200,
        visibility = 10000,
        windDegrees = 0,
        windSpeed = 1.2
    )
}

fun createSecondCurrent(): Current {
    return Current(
        clouds = 0,
        country = "ES",
        deltaTime = 1628596114000,
        humidity = 42,
        description = "cielo claro",
        icon = "01d",
        id = 3112989,
        location = Location(40.4381, -3.8196),
        name = "Pozuelo de Alarcón",
        pressure = 1016,
        rain = 0.0,
        snow = 0.0,
        sunrise = 1628745263000,
        sunset = 1628794813000,
        temp = 304.15,
        tempFeelsLike = 304.15,
        timeZone = 7200,
        visibility = 10000,
        windDegrees = 205,
        windSpeed = 2.5
    )
}