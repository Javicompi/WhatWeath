package es.jnsoft.data.mapper

import es.jnsoft.data.model.DailyData
import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Location

object DailyDataMapper : DataMapper<DailyData, Daily> {

    override fun mapToDomain(source: DailyData): Daily {
        return Daily(
            clouds = source.clouds,
            deltaTime = source.deltaTime,
            description = source.description,
            dewPoint = source.dewPoint,
            humidity = source.humidity,
            icon = source.icon,
            location = Location(source.lat, source.lon),
            moonPhase = source.moonPhase,
            pop = source.pop,
            pressure = source.pressure,
            rain = source.rain,
            snow = source.snow,
            sunrise = source.sunrise,
            sunset = source.sunset,
            tempMax = source.tempMax,
            tempMin = source.tempMin,
            timeZone = source.timeZone,
            uvi = source.uvi,
            windDegrees = source.windDegrees,
            windSpeed = source.windSpeed
        )
    }

    override fun mapFromDomain(source: Daily): DailyData {
        return DailyData(
            clouds = source.clouds,
            deltaTime = source.deltaTime,
            description = source.description,
            dewPoint = source.dewPoint,
            humidity = source.humidity,
            icon = source.icon,
            lat = source.location.lat,
            lon = source.location.lon,
            moonPhase = source.moonPhase,
            pop = source.pop,
            pressure = source.pressure,
            rain = source.rain,
            snow = source.snow,
            sunrise = source.sunrise,
            sunset = source.sunset,
            tempMax = source.tempMax,
            tempMin = source.tempMin,
            timeZone = source.timeZone,
            uvi = source.uvi,
            windDegrees = source.windDegrees,
            windSpeed = source.windSpeed
        )
    }

    override fun mapToDomainList(source: List<DailyData>): List<Daily> {
        return source.map { dailyData ->
            mapToDomain(dailyData)
        }
    }

    override fun mapFromDomainList(source: List<Daily>): List<DailyData> {
        return source.map { dailyDomain ->
            mapFromDomain(dailyDomain)
        }
    }
}