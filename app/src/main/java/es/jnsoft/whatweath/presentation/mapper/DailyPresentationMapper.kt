package es.jnsoft.whatweath.presentation.mapper

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.model.Daily
import es.jnsoft.whatweath.presentation.model.DailyPresentation
import es.jnsoft.whatweath.utils.convertLongToTime
import es.jnsoft.whatweath.utils.convertLongToWeekDay
import es.jnsoft.whatweath.utils.daytimeDuration
import java.math.RoundingMode
import kotlin.math.roundToInt

fun Daily.toPresentation(units: Units): DailyPresentation {
    return DailyPresentation(
        clouds = "$clouds %",
        dayText = convertLongToWeekDay(deltaTime, timeZone),
        deltaTime = deltaTime,
        dayTimeDuration = daytimeDuration(sunrise, sunset),
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
        moonPhase = moonPhase,
        pop = pop,
        pressure = "$pressure hPa",
        rain = when (units) {
            Units.STANDARD -> rain
            Units.METRIC -> rain
            Units.IMPERIAL -> {
                (rain / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toDouble()
            }
        },
        rainText = when (units) {
            Units.STANDARD -> "$rain mm"
            Units.METRIC -> "$rain mm"
            Units.IMPERIAL -> {
                (rain / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toString() + " in"
            }
        },
        snow = when (units) {
            Units.STANDARD -> snow
            Units.METRIC -> snow
            Units.IMPERIAL -> {
                (snow / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toDouble()
            }
        },
        snowText = when (units) {
            Units.STANDARD -> "$snow mm"
            Units.METRIC -> "$snow mm"
            Units.IMPERIAL -> {
                (snow / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toString() + " in"
            }
        },
        sunrise = sunrise,
        sunriseText = convertLongToTime(sunrise, timeZone),
        sunset = sunset,
        sunsetText = convertLongToTime(sunset, timeZone),
        tempMax = when (units) {
            Units.STANDARD -> tempMax.roundToInt().toString() + " K"
            Units.METRIC -> (tempMax - 273.15).roundToInt().toString() + " ºC"
            Units.IMPERIAL -> ((tempMax - 273.15) * 9 / 5 + 32).roundToInt().toString() + " ºF"
        },
        tempMin = when (units) {
            Units.STANDARD -> tempMin.roundToInt().toString() + " K"
            Units.METRIC -> (tempMin - 273.15).roundToInt().toString() + " ºC"
            Units.IMPERIAL -> ((tempMin - 273.15) * 9 / 5 + 32).roundToInt().toString() + " ºF"
        },
        timeZone = timeZone,
        uvi = uvi,
        windDegrees = windDegrees,
        windDegreesText = when {
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
        windSpeed = windSpeed,
        windSpeedText = when (units) {
            Units.STANDARD -> windSpeed.roundToInt().toString() + " ms"
            Units.METRIC -> (windSpeed * 3.6).roundToInt().toString() + " kmh"
            Units.IMPERIAL -> (windSpeed * 2.237).roundToInt().toString() + " mph"
        }
    )
}