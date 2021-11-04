package es.jnsoft.whatweath.presentation.mapper

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.model.Hourly
import es.jnsoft.whatweath.presentation.model.HourlyPresentation
import java.math.RoundingMode
import kotlin.math.roundToInt

fun Hourly.toPresentation(units: Units): HourlyPresentation {
    return HourlyPresentation(
        clouds = "$clouds %",
        deltaTime = deltaTime,
        description = description,
        dewPoint = when (units) {
            Units.STANDARD -> dewPoint.roundToInt().toString() + " K"
            Units.METRIC -> (dewPoint - 273.15).roundToInt().toString() + " ºC"
            Units.IMPERIAL -> ((dewPoint - 273.15) * 9 / 5 + 32).roundToInt().toString() + " ºF"
        },
        humidity = "$humidity %",
        icon = icon,
        lat = location.lat,
        lon = location.lon,
        pop = (pop * 100).roundToInt(),
        pressure = pressure,
        rain = when (units) {
            Units.STANDARD -> "$rain mm"
            Units.METRIC -> "$rain mm"
            Units.IMPERIAL -> {
                (rain / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toString() + " in"
            }
        },
        snow = when (units) {
            Units.STANDARD -> "$snow mm"
            Units.METRIC -> "$snow mm"
            Units.IMPERIAL -> {
                (snow / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toString() + " in"
            }
        },
        temp = temp,
        tempText = when (units) {
            Units.STANDARD -> temp.roundToInt().toString() + " K"
            Units.METRIC -> (temp - 273.15).roundToInt().toString() + " ºC"
            Units.IMPERIAL -> ((temp - 273.15) * 9 / 5 + 32).roundToInt().toString() + " ºF"
        },
        tempFeelsLike = when (units) {
            Units.STANDARD -> tempFeelsLike.roundToInt().toString() + " K"
            Units.METRIC -> (tempFeelsLike - 273.15).roundToInt().toString() + " ºC"
            Units.IMPERIAL -> ((tempFeelsLike - 273.15) * 9 / 5 + 32).roundToInt()
                .toString() + " ºF"
        },
        timeZone = timeZone,
        uvi = uvi,
        visibility = when (units) {
            Units.STANDARD -> "$visibility ms"
            Units.METRIC -> (visibility / 1000).toString() + " km/s"
            Units.IMPERIAL -> (visibility / 1609).toString() + " mi/s"
        },
        windDegrees = when {
            windDegrees <= 11 -> "N"
            windDegrees <= 33 -> "NNE"
            windDegrees <= 56 -> "NE"
            windDegrees <= 78 -> "ENE"
            windDegrees <= 101 -> "E"
            windDegrees <= 123 -> "ESE"
            windDegrees <= 146 -> "SE"
            windDegrees <= 168 -> "SSE"
            windDegrees <= 191 -> "S"
            windDegrees <= 213 -> "SSW"
            windDegrees <= 236 -> "SW"
            windDegrees <= 258 -> "WSW"
            windDegrees <= 281 -> "W"
            windDegrees <= 303 -> "WNW"
            windDegrees <= 326 -> "NW"
            windDegrees <= 348 -> "NNW"
            else -> "N"
        },
        windSpeed = when (units) {
            Units.STANDARD -> windSpeed.roundToInt().toString() + " ms"
            Units.METRIC -> (windSpeed * 3.6).roundToInt().toString() + " kmh"
            Units.IMPERIAL -> (windSpeed * 2.237).roundToInt().toString() + " mph"
        }
    )
}