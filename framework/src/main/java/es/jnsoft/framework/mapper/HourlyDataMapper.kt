package es.jnsoft.framework.mapper

import es.jnsoft.data.model.HourlyData
import es.jnsoft.framework.local.model.HourlyEntity
import es.jnsoft.framework.remote.model.HourlyResponse

fun List<HourlyData>.mapFromResponse(source: HourlyResponse): List<HourlyData> {
    return source.hourly.map { hourly ->
        HourlyData(
            clouds = hourly.clouds,
            deltaTime = hourly.dt.toLong() * 1000,
            description = hourly.weather[0].description.replaceFirstChar { it.uppercase() },
            dewPoint = hourly.dewPoint,
            humidity = hourly.humidity,
            icon = hourly.weather[0].icon,
            lat = source.lat,
            lon = source.lon,
            pop = (hourly.pop * 100).toInt(),
            pressure = hourly.pressure,
            rain = hourly.rain?.oneH ?: 0.0,
            snow = hourly.snow?.oneH ?: 0.0,
            temp = hourly.temp,
            tempFeelsLike = hourly.feelsLike,
            timeZone = source.timezoneOffset,
            uvi = hourly.uvi,
            visibility = hourly.visibility,
            windDegrees = hourly.windDeg,
            windSpeed = hourly.windSpeed
        )
    }
}

fun List<HourlyData>.mapToEntityList(): List<HourlyEntity> {
    return map { hourly ->
        HourlyEntity(
            clouds = hourly.clouds,
            deltaTime = hourly.deltaTime,
            description = hourly.description,
            dewPoint = hourly.dewPoint,
            humidity = hourly.humidity,
            icon = hourly.icon,
            latitude = hourly.lat,
            longitude = hourly.lon,
            pop = hourly.pop,
            pressure = hourly.pressure,
            rain = hourly.rain,
            snow = hourly.snow,
            temp = hourly.temp,
            tempFeelsLike = hourly.tempFeelsLike,
            timeZone = hourly.timeZone,
            uvi = hourly.uvi,
            visibility = hourly.visibility,
            windDegrees = hourly.windDegrees,
            windSpeed = hourly.windSpeed
        )
    }
}

fun List<HourlyData>.mapFromEntityList(entityList: List<HourlyEntity>): List<HourlyData> {
    return entityList.map { entity ->
        HourlyData(
            clouds = entity.clouds,
            deltaTime = entity.deltaTime,
            description = entity.description,
            dewPoint = entity.dewPoint,
            humidity = entity.humidity,
            icon = entity.icon,
            lat = entity.latitude,
            lon = entity.longitude,
            pop = entity.pop,
            pressure = entity.pressure,
            rain = entity.rain,
            snow = entity.snow,
            temp = entity.temp,
            tempFeelsLike = entity.tempFeelsLike,
            timeZone = entity.timeZone,
            uvi = entity.uvi,
            visibility = entity.visibility,
            windDegrees = entity.windDegrees,
            windSpeed = entity.windSpeed
        )
    }
}