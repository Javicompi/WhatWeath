package es.jnsoft.data.model

data class CurrentData(
    val clouds: Int,
    val country: String,
    val deltaTime: Long,
    val description: String,
    val humidity: Int,
    val icon: String,
    val id: Long,
    val lat: Double,
    val lon: Double,
    val name: String,
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val tempFeelsLike: Double,
    val timeZone: Int,
    val visibility: Int,
    val windDegrees: Int,
    val windSpeed: Double
) : BaseData