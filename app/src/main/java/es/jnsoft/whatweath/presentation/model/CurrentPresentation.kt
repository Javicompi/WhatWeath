package es.jnsoft.whatweath.presentation.model

class CurrentPresentation(
    val clouds: String,
    val country: String,
    val deltaTime: Long,
    val description: String,
    val humidity: String,
    val icon: String,
    val id: Long,
    val lat: Double,
    val lon: Double,
    val name: String,
    val pressure: String,
    val rain: Double,
    val rainText: String,
    val snow: Double,
    val snowText: String,
    val sunrise: Long,
    val sunset: Long,
    val temp: String,
    val tempFeelsLike: String,
    val timeZone: Int,
    val visibility: Int,
    val windDegrees: Int,
    val windDegreesText: String,
    val windSpeed: String
) : BasePresentation
