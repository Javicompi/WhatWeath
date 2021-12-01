package es.jnsoft.framework.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyResponse(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "timezone")
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int,
    @Json(name = "daily")
    val daily: List<Daily>
) {
    @JsonClass(generateAdapter = true)
    data class Daily(
        @Json(name = "dt")
        val dt: Int,
        @Json(name = "sunrise")
        val sunrise: Int,
        @Json(name = "sunset")
        val sunset: Int,
        @Json(name = "moonrise")
        val moonrise: Int,
        @Json(name = "moonset")
        val moonset: Int,
        @Json(name = "moon_phase")
        val moonPhase: Double,
        @Json(name = "temp")
        val temp: Temp,
        @Json(name = "feels_like")
        val feelsLike: FeelsLike,
        @Json(name = "pressure")
        val pressure: Int,
        @Json(name = "humidity")
        val humidity: Int,
        @Json(name = "dew_point")
        val dewPoint: Double,
        @Json(name = "wind_speed")
        val windSpeed: Double,
        @Json(name = "wind_deg")
        val windDeg: Int,
        @Json(name = "wind_gust")
        val windGust: Double,
        @Json(name = "weather")
        val weather: List<Weather>,
        @Json(name = "clouds")
        val clouds: Int,
        @Json(name = "pop")
        val pop: Double,
        @Json(name = "uvi")
        val uvi: Double,
        @Json(name = "rain")
        val rain: Double?,
        @Json(name = "snow")
        val snow: Double?
    ) {
        @JsonClass(generateAdapter = true)
        data class Temp(
            @Json(name = "day")
            val day: Double,
            @Json(name = "min")
            val min: Double,
            @Json(name = "max")
            val max: Double,
            @Json(name = "night")
            val night: Double,
            @Json(name = "eve")
            val eve: Double,
            @Json(name = "morn")
            val morn: Double
        )

        @JsonClass(generateAdapter = true)
        data class FeelsLike(
            @Json(name = "day")
            val day: Double,
            @Json(name = "night")
            val night: Double,
            @Json(name = "eve")
            val eve: Double,
            @Json(name = "morn")
            val morn: Double
        )

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
}