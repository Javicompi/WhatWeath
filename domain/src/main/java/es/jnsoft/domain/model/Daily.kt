package es.jnsoft.domain.model

data class Daily(
    val clouds: Int,
    val deltaTime: Long,
    val description: String,
    val dewPoint: Double,
    val humidity: Int,
    val icon: String,
    val location: Location,
    val moonPhase: Double,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val sunrise: Long,
    val sunset: Long,
    val tempMax: Double,
    val tempMin: Double,
    val timeZone: Int,
    val uvi: Double,
    val visibility: Int,
    val windDegrees: Int,
    val windSpeed: Double
)