package es.jnsoft.data

import es.jnsoft.data.model.CurrentData
import es.jnsoft.data.model.HourlyData


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

fun createHourly(lat: Double = 10.0, lon: Double = 10.0): HourlyData {
    return HourlyData(
        clouds = 20,
        deltaTime = System.currentTimeMillis(),
        description = "algo de nubes",
        dewPoint = 287.47,
        humidity = 65,
        icon = "02d",
        lat = lat,
        lon = lon,
        pop = 0,
        pressure = 1019,
        rain = 0.0,
        snow = 0.0,
        temp = 294.3,
        tempFeelsLike = 294.17,
        timeZone = 7200,
        uvi = 1.34,
        visibility = 10000,
        windDegrees = 123,
        windSpeed = 2.13
    )
}

fun createHourlyList(lat: Double, lon: Double): List<HourlyData> {
    val firstHourly = createHourly(lat, lon)
    val secondHourly = firstHourly.copy(deltaTime = firstHourly.deltaTime + 3600000)
    val thirdHourly = secondHourly.copy(deltaTime = secondHourly.deltaTime + 3600000)
    return listOf(firstHourly, secondHourly, thirdHourly)
}