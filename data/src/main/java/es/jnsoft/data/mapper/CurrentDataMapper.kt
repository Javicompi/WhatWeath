package es.jnsoft.data.mapper

import es.jnsoft.data.model.CurrentData
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Location

object CurrentDataMapper : DataMapper<CurrentData, Current> {

    override fun mapToDomain(source: CurrentData): Current {
        return Current(
            clouds = source.clouds,
            country = source.country,
            deltaTime = source.deltaTime,
            description = source.description,
            humidity = source.humidity,
            icon = source.icon,
            id = source.id,
            location = Location(lat = source.lat, lon = source.lon),
            name = source.name,
            pressure = source.pressure,
            rain = source.rain,
            snow = source.snow,
            sunrise = source.sunrise,
            sunset = source.sunset,
            temp = source.temp,
            tempFeelsLike = source.tempFeelsLike,
            timeZone = source.timeZone,
            visibility = source.visibility,
            windDegrees = source.windDegrees,
            windSpeed = source.windSpeed
        )
    }

    override fun mapFromDomain(source: Current): CurrentData {
        return CurrentData(
            clouds = source.clouds,
            country = source.country,
            deltaTime = source.deltaTime,
            description = source.description,
            humidity = source.humidity,
            icon = source.icon,
            id = source.id,
            lat = source.location.lat,
            lon = source.location.lon,
            name = source.name,
            pressure = source.pressure,
            rain = source.rain,
            snow = source.snow,
            sunrise = source.sunrise,
            sunset = source.sunset,
            temp = source.temp,
            tempFeelsLike = source.tempFeelsLike,
            timeZone = source.timeZone,
            visibility = source.visibility,
            windDegrees = source.windDegrees,
            windSpeed = source.windSpeed
        )
    }

    override fun mapToDomainList(source: List<CurrentData>): List<Current> {
        return source.map { currentData ->
            mapToDomain(currentData)
        }
    }

    override fun mapFromDomainList(source: List<Current>): List<CurrentData> {
        return source.map { currentDomain ->
            mapFromDomain(currentDomain)
        }
    }
}