package es.jnsoft.framework.database

import es.jnsoft.data.model.HourlyData
import es.jnsoft.framework.local.model.CurrentEntity

fun createFirstCurrent(): CurrentEntity {
    return CurrentEntity(
        clouds = 40,
        country = "ES",
        deltaTime = 1628595794000,
        humidity = 50,
        description = "nubes dispersas",
        icon = "03d",
        id = 6697298,
        latitude = 38.2324,
        longitude = -0.547,
        name = "Gran Alacant",
        pressure = 1016,
        rain = 0.0,
        snow = 0.0,
        sunrise = 1628745263000,
        sunset = 1628794813000,
        temp = 303.15,
        tempFeelsLike = 304.20,
        timeZone = 7200,
        visibility = 10000,
        windDegrees = 0,
        windSpeed = 1.25
    )
}

fun createSecondCurrent(): CurrentEntity {
    return CurrentEntity(
        clouds = 0,
        country = "ES",
        deltaTime = 1628596114000,
        humidity = 42,
        description = "cielo claro",
        icon = "01d",
        id = 3112989,
        latitude = 40.4381,
        longitude = -3.8196,
        name = "Pozuelo de Alarcón",
        pressure = 1016,
        rain = 0.0,
        snow = 0.0,
        sunrise = 1628745263000,
        sunset = 1628794813000,
        temp = 304.25,
        tempFeelsLike = 304.75,
        timeZone = 7200,
        visibility = 10000,
        windDegrees = 205,
        windSpeed = 2.31
    )
}

fun createHourly(lat: Double = 53.3244, lon: Double = -6.3858): HourlyData {
    return HourlyData(
        clouds = 20,
        deltaTime = 1635325200000,
        description = "algo de nubes",
        dewPoint = 0.5,
        humidity = 60,
        icon = "02n",
        lat = lat,
        lon = lon,
        pop = 50,
        pressure = 1017,
        rain = 0.2,
        snow = 0.0,
        temp = 289.17,
        tempFeelsLike = 289.1,
        timeZone = 3600,
        uvi = 0.08,
        visibility = 5000,
        windDegrees = 203,
        windSpeed = 6.34
    )
}

fun createHourlyList(lat: Double, lon: Double): List<HourlyData> {
    val first = createHourly(lat, lon)
    val second = first.copy(deltaTime = first.deltaTime + 3600000)
    val third = second.copy(deltaTime = second.deltaTime + 3600000)
    return listOf(first, second, third)
}