package es.jnsoft.data.model

data class HourlyData(
    val clouds: Int,
    val deltaTime: Long,
    val description: String,
    val dewPoint: Double,
    val humidity: Int,
    val icon: String,
    val lat: Double,
    val lon: Double,
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
) : BaseData
