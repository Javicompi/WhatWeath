package es.jnsoft.whatweath.presentation.mapper

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.model.Current
import es.jnsoft.whatweath.presentation.model.BasePresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlin.math.roundToInt

fun Current.toPresentation(units: Units): CurrentPresentation {
    return CurrentPresentation(
        clouds = clouds,
        country = country,
        deltaTime = deltaTime,
        description = description,
        humidity = humidity,
        icon = icon,
        id = id,
        lat = location.lat,
        lon = location.lon,
        name = name,
        pressure = pressure,
        rain = rain,
        snow = snow,
        sunrise = sunrise,
        sunset = sunset,
        temp = when(units) {
            Units.STANDARD -> temp.roundToInt()
            Units.METRIC -> (temp - 273.15).roundToInt()
            Units.IMPERIAL -> ((temp - 273.15) * 9 / 5 + 32).roundToInt()
        },
        tempFeelsLike = when(units) {
            Units.STANDARD -> tempFeelsLike.roundToInt()
            Units.METRIC -> (tempFeelsLike - 273.15).roundToInt()
            Units.IMPERIAL -> ((tempFeelsLike - 273.15) * 9 / 5 + 32).roundToInt()
        },
        timeZone = timeZone,
        visibility = visibility,
        windDegrees = windDegrees,
        windSpeed = when(units) {
            Units.STANDARD -> windSpeed.roundToInt()
            Units.METRIC -> (windSpeed * 3.6).roundToInt()
            Units.IMPERIAL -> (windSpeed * 23237).roundToInt()
        }
    )
}