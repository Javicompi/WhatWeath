package es.jnsoft.whatweath.presentation.mapper

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.model.Current
import es.jnsoft.whatweath.presentation.model.BasePresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlin.math.roundToInt

object CurrentPresentationConverter : PresentationMapper<Current, BasePresentation, Units> {

    override fun mapToPresentation(domain: Current, units: Units): BasePresentation {
        return CurrentPresentation(
            clouds = domain.clouds,
            country = domain.country,
            deltaTime = domain.deltaTime,
            description = domain.description,
            humidity = domain.humidity,
            icon = domain.icon,
            id = domain.id,
            lat = domain.location.lat,
            lon = domain.location.lon,
            name = domain.name,
            pressure = domain.pressure,
            sunrise = domain.sunrise,
            sunset = domain.sunset,
            timeZone = domain.timeZone,
            visibility = domain.visibility,
            windDegrees = domain.windDegrees,
            temp = when(units) {
                Units.STANDARD -> {
                    domain.temp.roundToInt()
                }
                Units.METRIC -> {
                    (domain.temp - 273.15).roundToInt()
                }
                Units.IMPERIAL -> {
                    ((domain.temp - 273.15) * 9 / 5 + 32).roundToInt()
                }
            },
            tempFeelsLike = when(units) {
                Units.STANDARD -> {
                    domain.tempFeelsLike.roundToInt()
                }
                Units.METRIC -> {
                    (domain.tempFeelsLike - 273.15).roundToInt()
                }
                Units.IMPERIAL -> {
                    ((domain.tempFeelsLike - 273.15) * 9 / 5 + 32).roundToInt()
                }
            },
            windSpeed = when(units) {
                Units.STANDARD -> {
                    domain.windSpeed.roundToInt()
                }
                Units.METRIC -> {
                    (domain.windSpeed * 3.6).roundToInt()
                }
                Units.IMPERIAL -> {
                    (domain.windSpeed * 2.237).roundToInt()
                }
            },
            rain = when(units) {
                Units.STANDARD -> {
                    domain.rain
                }
                Units.METRIC -> {
                    domain.rain
                }
                Units.IMPERIAL -> {
                    domain.rain / 25.4
                }
            },
            snow = when(units) {
                Units.STANDARD -> {
                    domain.snow
                }
                Units.METRIC -> {
                    domain.snow
                }
                Units.IMPERIAL -> {
                    domain.snow / 25.4
                }
            }
        )
    }
}