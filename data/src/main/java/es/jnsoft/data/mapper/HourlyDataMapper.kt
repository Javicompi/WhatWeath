package es.jnsoft.data.mapper

import es.jnsoft.data.model.HourlyData
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Location

object HourlyDataMapper : DataMapper<HourlyData, Hourly> {

    override fun mapToDomain(source: HourlyData): Hourly {
        return Hourly(
            cityId = source.cityId,
            clouds = source.clouds,
            deltaTime = source.deltaTime,
            description = source.description,
            dewPoint = source.dewPoint,
            humidity = source.humidity,
            icon = source.icon,
            id = source.id,
            location = Location(source.lat, source.lon),
            pop = source.pop,
            pressure = source.pressure,
            rain = source.rain,
            snow = source.snow,
            temp = source.temp,
            tempFeelsLike = source.tempFeelsLike,
            timeZone = source.timeZone,
            uvi = source.uvi,
            visibility = source.visibility,
            windDegrees = source.windDegrees,
            windSpeed = source.windSpeed
        )
    }

    override fun mapFromDomain(source: Hourly): HourlyData {
        return HourlyData(
            cityId = source.cityId,
            clouds = source.clouds,
            deltaTime = source.deltaTime,
            description = source.description,
            dewPoint = source.dewPoint,
            humidity = source.humidity,
            icon = source.icon,
            id = source.id,
            lat = source.location.lat,
            lon = source.location.lon,
            pop = source.pop,
            pressure = source.pressure,
            rain = source.rain,
            snow = source.snow,
            temp = source.temp,
            tempFeelsLike = source.tempFeelsLike,
            timeZone = source.timeZone,
            uvi = source.uvi,
            visibility = source.visibility,
            windDegrees = source.windDegrees,
            windSpeed = source.windSpeed
        )
    }

    override fun mapToDomainList(source: List<HourlyData>): List<Hourly> {
        return source.map { hourlyData ->
            mapToDomain(hourlyData)
        }
    }

    override fun mapFromDomainList(source: List<Hourly>): List<HourlyData> {
        return source.map { hourlyDomain ->
            mapFromDomain(hourlyDomain)
        }
    }
}