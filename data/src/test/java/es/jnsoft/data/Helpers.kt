package es.jnsoft.data

import es.jnsoft.data.model.CurrentData


fun createFirstCurrent(): CurrentData {
    return CurrentData(
        clouds = 40,
        country = "ES",
        deltaTime = 1628595794000,
        humidity = 50,
        description = "nubes dispersas",
        icon = "03d",
        id = 6697298,
        lat = 38.2324,
        lon = -0.547,
        name = "Gran Alacant",
        pressure = 1016,
        rain = 0.0,
        snow = 0.0,
        sunrise = 1628745263000,
        sunset = 1628794813000,
        temp = 303.15,
        tempFeelsLike = 304.25,
        timeZone = 7200,
        visibility = 10000,
        windDegrees = 0,
        windSpeed = 1.5
    )
}

fun createSecondCurrent(): CurrentData {
    return CurrentData(
        clouds = 0,
        country = "ES",
        deltaTime = 1628596114000,
        humidity = 42,
        description = "cielo claro",
        icon = "01d",
        id = 3112989,
        lat = 40.4381,
        lon = -3.8196,
        name = "Pozuelo de Alarcón",
        pressure = 1016,
        rain = 0.0,
        snow = 0.0,
        sunrise = 1628745263000,
        sunset = 1628794813000,
        temp = 304.15,
        tempFeelsLike = 304.75,
        timeZone = 7200,
        visibility = 10000,
        windDegrees = 205,
        windSpeed = 2.5
    )
}