package es.jnsoft.whatweath.presentation.model

data class HourlyPresentation(
    val clouds: String,
    val deltaTime: Long,
    val description: String,
    val dewPoint: String,
    val humidity: String,
    val icon: String,
    val lat: Double,
    val lon: Double,
    val pop: Int,
    val pressure: Int,
    val rain: String,
    val snow: String,
    val temp: Double,
    val tempText: String,
    val tempFeelsLike: String,
    val timeZone: Int,
    val uvi: Double,
    val visibility: String,
    val windDegrees: String,
    val windSpeed: String
) : BasePresentation
