package es.jnsoft.framework.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HourlyResponse(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int,
    @Json(name = "hourly")
    val hourly: List<Hourly>
) {
    @JsonClass(generateAdapter = true)
    data class Hourly(
        @Json(name = "dt")
        val dt: Int,
        @Json(name = "temp")
        val temp: Double,
        @Json(name = "feels_like")
        val feelsLike: Double,
        @Json(name = "pressure")
        val pressure: Int,
        @Json(name = "humidity")
        val humidity: Int,
        @Json(name = "dew_point")
        val dewPoint: Double,
        @Json(name = "uvi")
        val uvi: Double,
        @Json(name = "clouds")
        val clouds: Int,
        @Json(name = "visibility")
        val visibility: Int,
        @Json(name = "wind_speed")
        val windSpeed: Double,
        @Json(name = "wind_deg")
        val windDeg: Int,
        @Json(name = "wind_gust")
        val windGust: Double,
        @Json(name = "weather")
        val weather: List<Weather>,
        @Json(name = "pop")
        val pop: Double,
        @Json(name = "rain")
        val rain: Rain?,
        @Json(name = "snow")
        val snow: Snow?
    ) {
        @JsonClass(generateAdapter = true)
        data class Weather(
            @Json(name = "id")
            val id: Int,
            @Json(name = "main")
            val main: String,
            @Json(name = "description")
            val description: String,
            @Json(name = "icon")
            val icon: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class Rain(
        @Json(name = "1h")
        val oneH: Double?
    )

    @JsonClass(generateAdapter = true)
    data class Snow(
        @Json(name = "1h")
        val oneH: Double?
    )
}