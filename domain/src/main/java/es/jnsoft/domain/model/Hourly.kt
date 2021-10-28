package es.jnsoft.domain.model

data class Hourly(
    val cityId: Long,
    val clouds: Int,
    val deltaTime: Long,
    val description: String,
    val dewPoint: Double,
    val humidity: Int,
    val icon: String,
    val id: Long,
    val location: Location,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val temp: Double,
    val tempFeelsLike: Double,
    val timeZone: Int,
    val uvi: Double,
    val visibility: Int,
    val windDegrees: Int,
    val windSpeed: Double
)
