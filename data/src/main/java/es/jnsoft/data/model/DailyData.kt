package es.jnsoft.data.model

data class DailyData(
    val clouds: Int,
    val deltaTime: Long,
    val description: String,
    val dewPoint: Double,
    val humidity: Int,
    val icon: String,
    val lat: Double,
    val lon: Double,
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
    val windDegrees: Int,
    val windSpeed: Double
) : BaseData
