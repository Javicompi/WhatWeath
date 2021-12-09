package es.jnsoft.whatweath.utils

import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Location
import java.text.SimpleDateFormat
import java.util.*

fun createHourly(lat: Double, lon: Double): Hourly {
    return Hourly(
        clouds = 0,
        deltaTime = 0,
        description = "",
        dewPoint = 0.0,
        humidity = 0,
        icon = "",
        location = Location(lat, lon),
        pop = 0,
        pressure = 0,
        rain = 0.0,
        snow = 0.0,
        temp = 0.0,
        tempFeelsLike = 0.0,
        timeZone = 0,
        uvi = 0.0,
        visibility = 0,
        windDegrees = 0,
        windSpeed = 0.0
    )
}

fun createDaily(lat: Double, lon: Double): Daily {
    return Daily(
        clouds = 0,
        deltaTime = 0,
        description = "",
        dewPoint = 0.0,
        humidity = 0,
        icon = "",
        location = Location(lat, lon),
        moonPhase = 0,
        pop = 0,
        pressure = 0,
        rain = 0.0,
        snow = 0.0,
        sunrise = 0,
        sunset = 0,
        tempMax = 0.0,
        tempMin = 0.0,
        timeZone = 0,
        uvi = 0.0,
        windDegrees = 0,
        windSpeed = 0.0
    )
}

fun convertLongToTime(time: Long, offset: Int): String {
    val date = Date(time)
    date.time += offset.toLong() * 1000
    val format = SimpleDateFormat("HH:mm")
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}

fun daytimeDuration(sunrise: Long, sunset: Long): String {
    val time = Date(sunset - sunrise)
    val format = SimpleDateFormat("HH:mm")
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(time)
}