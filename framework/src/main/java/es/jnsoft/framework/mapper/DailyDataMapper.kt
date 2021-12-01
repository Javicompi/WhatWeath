package es.jnsoft.framework.mapper

import es.jnsoft.data.model.DailyData
import es.jnsoft.framework.local.model.DailyEntity
import es.jnsoft.framework.remote.model.DailyResponse

fun List<DailyData>.mapFromResponse(source: DailyResponse): List<DailyData> {
    return source.daily.map { daily ->
        DailyData(
            clouds = daily.clouds,
            deltaTime = daily.dt.toLong() * 1000,
            description = daily.weather[0].description,
            dewPoint = daily.dewPoint,
            humidity = daily.humidity,
            icon = daily.weather[0].icon,
            lat = source.lat,
            lon = source.lon,
            moonPhase = (daily.moonPhase * 100).toInt(),
            pop = (daily.pop * 100).toInt(),
            pressure = daily.pressure,
            rain = daily.rain ?: 0.0,
            snow = daily.snow ?: 0.0,
            sunrise = daily.sunrise.toLong() * 1000,
            sunset = daily.sunset.toLong() * 1000,
            tempMax = daily.temp.max,
            tempMin = daily.temp.min,
            timeZone = source.timezoneOffset,
            uvi = daily.uvi,
            windDegrees = daily.windDeg,
            windSpeed = daily.windSpeed
        )
    }
}

fun List<DailyData>.mapToEntityList(): List<DailyEntity> {
    return map { daily ->
        DailyEntity(
            clouds = daily.clouds,
            deltaTime = daily.deltaTime,
            description = daily.description.replaceFirstChar { it.uppercase() },
            dewPoint = daily.dewPoint,
            humidity = daily.humidity,
            icon = daily.icon,
            latitude = daily.lat,
            longitude = daily.lon,
            moonPhase = daily.moonPhase,
            pop = daily.pop,
            pressure = daily.pressure,
            rain = daily.rain,
            snow = daily.snow,
            sunrise = daily.sunrise,
            sunset = daily.sunset,
            tempMax = daily.tempMax,
            tempMin = daily.tempMin,
            timeZone = daily.timeZone,
            uvi = daily.uvi,
            windDegrees = daily.windDegrees,
            windSpeed = daily.windSpeed
        )
    }
}

fun List<DailyData>.mapFromEntityList(entityList: List<DailyEntity>): List<DailyData> {
    return entityList.map { entity ->
        DailyData(
            clouds = entity.clouds,
            deltaTime = entity.deltaTime,
            description = entity.description,
            dewPoint = entity.dewPoint,
            humidity = entity.humidity,
            icon = entity.icon,
            lat = entity.latitude,
            lon = entity.longitude,
            moonPhase = entity.moonPhase,
            pop = entity.pop,
            pressure = entity.pressure,
            rain = entity.rain,
            snow = entity.snow,
            sunrise = entity.sunrise,
            sunset = entity.sunset,
            tempMax = entity.tempMax,
            tempMin = entity.tempMin,
            timeZone = entity.timeZone,
            uvi = entity.uvi,
            windDegrees = entity.windDegrees,
            windSpeed = entity.windSpeed
        )
    }
}