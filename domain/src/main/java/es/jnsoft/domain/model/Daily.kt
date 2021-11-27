package es.jnsoft.domain.model

data class Daily(
    val clouds: Int,
    val deltaTime: Long,
    val description: String,
    val dewPoint: Double,
    val humidity: Int,
    val icon: String,
    val moonPhase: Double,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val sunrise: Long,
    val sunset: Long,
    val tempMax: Double,
    val tempMin: Double,
    val uvi: Double,
    val windDegrees: Int,
    val windSpeed: Double
)