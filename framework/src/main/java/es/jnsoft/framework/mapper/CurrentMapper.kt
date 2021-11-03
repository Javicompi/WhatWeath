package es.jnsoft.framework.mapper

import es.jnsoft.data.model.CurrentData
import es.jnsoft.framework.local.model.CurrentEntity
import es.jnsoft.framework.remote.model.CurrentResponse

fun CurrentResponse.mapToData(): CurrentData {
    return CurrentData(
        clouds = clouds.all,
        country = sys.country,
        deltaTime = dt.toLong() * 1000,
        description = weather[0].description.replaceFirstChar { it.uppercase() },
        humidity = main.humidity,
        icon = weather[0].icon,
        id = id.toLong(),
        lat = coord.lat,
        lon = coord.lon,
        name = name,
        pressure = main.pressure,
        rain = rain?.oneH ?: 0.0,
        snow = snow?.oneH ?: 0.0,
        sunrise = sys.sunrise.toLong() * 1000,
        sunset = sys.sunset.toLong() * 1000,
        temp = main.temp,
        tempFeelsLike = main.feelsLike,
        timeZone = timezone,
        visibility = visibility,
        windDegrees = wind?.deg ?: 0,
        windSpeed = wind?.speed ?: 0.0
    )
}

fun CurrentData.mapToEntity(): CurrentEntity {
    return CurrentEntity(
        clouds = clouds,
        country = country,
        deltaTime = deltaTime,
        description = description,
        humidity = humidity,
        icon = icon,
        id = id,
        latitude = lat,
        longitude = lon,
        name = name,
        pressure = pressure,
        rain = rain,
        snow = snow,
        sunrise = sunrise,
        sunset = sunset,
        temp = temp,
        tempFeelsLike = tempFeelsLike,
        timeZone = timeZone,
        visibility = visibility,
        windDegrees = windDegrees,
        windSpeed = windSpeed
    )
}

fun CurrentEntity.mapToData(): CurrentData {
    return CurrentData(
        clouds = clouds,
        country = country,
        deltaTime = deltaTime,
        description = description,
        humidity = humidity,
        icon = icon,
        id = id,
        lat = latitude,
        lon = longitude,
        name = name,
        pressure = pressure,
        rain = rain,
        snow = snow,
        sunrise = sunrise,
        sunset = sunset,
        temp = temp,
        tempFeelsLike = tempFeelsLike,
        timeZone = timeZone,
        visibility = visibility,
        windDegrees = windDegrees,
        windSpeed = windSpeed
    )
}