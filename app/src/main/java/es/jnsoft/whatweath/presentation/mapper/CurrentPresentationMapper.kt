package es.jnsoft.whatweath.presentation.mapper

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.model.Current
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun Current.toPresentation(units: Units): CurrentPresentation {
    return CurrentPresentation(
        clouds = "$clouds %",
        country = country,
        deltaTime = deltaTime,
        description = description,
        humidity = "$humidity %",
        icon = icon,
        id = id,
        lat = location.lat,
        lon = location.lon,
        name = name,
        pressure = "$pressure hPa",
        rain = rain,
        rainText = when (units) {
            Units.STANDARD -> "$rain mm"
            Units.METRIC -> "$rain mm"
            Units.IMPERIAL -> {
                (rain / 25.4).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN).toString() + " in"
            }
        },
        snow = snow,
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
        daytimeDuration = daytimeDuration(sunrise, sunset),
        temp = when (units) {
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
        visibility = when (units) {
            Units.STANDARD -> "$visibility ms"
            Units.METRIC -> (visibility / 1000).toString() + " km/s"
            Units.IMPERIAL -> (visibility / 1609).toString() + " mi/s"
        },
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
        windSpeed = when (units) {
            Units.STANDARD -> windSpeed.roundToInt().toString() + " ms"
            Units.METRIC -> (windSpeed * 3.6).roundToInt().toString() + " kmh"
            Units.IMPERIAL -> (windSpeed * 2.237).roundToInt().toString() + " mph"
        }
    )
}

private fun convertLongToTime(time: Long, offset: Int): String {
    val date = Date(time)
    date.time += offset.toLong() * 1000
    val format = SimpleDateFormat("HH:mm")
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}

private fun daytimeDuration(sunrise: Long, sunset: Long): String {
    val time = Date(sunset - sunrise)
    val format = SimpleDateFormat("HH:mm")
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(time)
}