package es.jnsoft.framework.datasource.remote

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.framework.remote.model.ErrorResponse
import es.jnsoft.framework.remote.model.HourlyResponse
import es.jnsoft.framework.remote.service.HourlyApiService
import kotlinx.coroutines.delay

class FakeHourlyApiService : HourlyApiService {

    override suspend fun findHourlyResponseByLatLon(
        latitude: Double,
        longitude: Double,
        units: String?,
        language: String,
        exclude: String
    ): NetworkResponse<HourlyResponse, ErrorResponse> {
        delay((500..2000).random().toLong())
        return if (latitude > 0.0 && longitude > 0.0) {
            NetworkResponse.Success(mockResponse(), null, 200)
        } else {
            NetworkResponse.ServerError(null, 400)
        }
    }

    private fun mockResponse(): HourlyResponse {
        val lat = 53.3244
        val lon = -6.3858
        val timeZone = "Europe/Dublin"
        val timeZoneOffset = 3600
        val firstHourly = createHourly()
        val secondHourly = firstHourly.copy(dt = firstHourly.dt + 3600)
        val thirdHourly = secondHourly.copy(dt = secondHourly.dt + 3600)
        return HourlyResponse(
            lat = lat,
            lon = lon,
            timezone = timeZone,
            timezoneOffset = timeZoneOffset,
            hourly = listOf(firstHourly, secondHourly, thirdHourly)
        )
    }

    private fun createHourly(): HourlyResponse.Hourly {
        return HourlyResponse.Hourly(
            dt = 1635343200,
            temp = 289.55,
            feelsLike = 289.54,
            pressure = 1003,
            humidity = 88,
            dewPoint = 287.56,
            uvi = 0.19,
            clouds = 80,
            visibility = 6422,
            windSpeed = 8.63,
            windDeg = 210,
            windGust = 16.11,
            weather = listOf(
                HourlyResponse.Hourly.Weather(
                    id = 500,
                    main = "Rain",
                    description = "lluvia ligera",
                    icon = "10d"
                )
            ),
            pop = 1.0,
            rain = HourlyResponse.Rain(oneH = 0.58),
            snow = HourlyResponse.Snow(oneH = 0.0)
        )
    }
}