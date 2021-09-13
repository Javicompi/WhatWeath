package es.jnsoft.framework.mapper

import es.jnsoft.data.model.CurrentData
import es.jnsoft.framework.local.model.CurrentEntity
import es.jnsoft.framework.remote.model.CurrentResponse

object CurrentDataMapper : BaseDataMapper<CurrentData, CurrentResponse, CurrentEntity> {

    override fun mapFromResponse(source: CurrentResponse): CurrentData {
        return CurrentData(
            clouds = source.clouds.all,
            country = source.sys.country,
            deltaTime = (source.dt * 1000).toLong(),
            description = source.weather[0].description.replaceFirstChar { it.uppercase() },
            humidity = source.main.humidity,
            icon = source.weather[0].icon,
            id = source.id.toLong(),
            lat = source.coord.lat,
            lon = source.coord.lon,
            name = source.name,
            pressure = source.main.pressure,
            rain = source.rain?.oneH ?: 0.0,
            snow = source.snow?.oneH ?: 0.0,
            sunrise = (source.sys.sunrise * 1000).toLong(),
            sunset = (source.sys.sunset * 1000).toLong(),
            temp = source.main.temp,
            tempFeelsLike = source.main.feelsLike,
            timeZone = source.timezone,
            visibility = source.visibility,
            windDegrees = source.wind?.deg ?: 0,
            windSpeed = source.wind?.speed ?: 0.0
        )
    }

    override fun mapToEntity(source: CurrentData): CurrentEntity {
        return CurrentEntity(
            clouds = source.clouds,
            country = source.country,
            deltaTime = source.deltaTime,
            description = source.description,
            humidity = source.humidity,
            icon = source.icon,
            id = source.id,
            latitude = source.lat,
            longitude = source.lon,
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

    override fun mapFromEntity(source: CurrentEntity): CurrentData {
        return CurrentData(
            clouds = source.clouds,
            country = source.country,
            deltaTime = source.deltaTime,
            description = source.description,
            humidity = source.humidity,
            icon = source.icon,
            id = source.id,
            lat = source.latitude,
            lon = source.longitude,
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
}