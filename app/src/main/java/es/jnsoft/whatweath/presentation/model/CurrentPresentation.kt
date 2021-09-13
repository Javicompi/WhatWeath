package es.jnsoft.whatweath.presentation.model

import es.jnsoft.domain.model.Current

class CurrentPresentation(
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
    val temp: Int,
    val tempFeelsLike: Int,
    val timeZone: Int,
    val visibility: Int,
    val windDegrees: Int,
    val windSpeed: Int
) : BasePresentation /*{

    override fun getFormattedTemp(): Int {
        TODO("Not yet implemented")
    }

    override fun getFormattedTempFL(): Int {
        TODO("Not yet implemented")
    }

    override fun getFormattedWindSpeed(): Int {
        TODO("Not yet implemented")
    }

    override fun getFormattedRain(): Double {
        TODO("Not yet implemented")
    }

    override fun getFormattedSnow(): Double {
        TODO("Not yet implemented")
    }
}*/
