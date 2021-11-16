package es.jnsoft.framework.datasource.remote

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.framework.remote.model.CurrentResponse
import es.jnsoft.framework.remote.model.ErrorResponse
import es.jnsoft.framework.remote.service.CurrentApiService
import kotlinx.coroutines.delay

class FakeCurrentApiService : CurrentApiService {

    override suspend fun findCurrentResponseByLatLon(
        latitude: Double,
        longitude: Double,
        units: String?,
        language: String
    ): NetworkResponse<CurrentResponse, ErrorResponse> {
        delay((500..2000).random().toLong())
        return if (latitude > 0.0 && longitude > 0.0) {
            NetworkResponse.Success(mockResponse(), null, 200)
        } else {
            NetworkResponse.ServerError(null, 400)
        }
    }

    override suspend fun findCurrentResponseByName(
        location: String,
        units: String?,
        language: String
    ): NetworkResponse<CurrentResponse, ErrorResponse> {
        delay((500..2000).random().toLong())
        return when {
            location.isEmpty() -> {
                NetworkResponse.ServerError(null, 400)
            }
            location.length <= 3 -> {
                NetworkResponse.ServerError(null, 404)
            }
            else -> {
                NetworkResponse.Success(mockResponse(), null, 200)
            }
        }
    }

    override suspend fun findCurrentResponseById(
        id: Long,
        units: String?,
        language: String
    ): NetworkResponse<CurrentResponse, ErrorResponse> {
        delay((500..2000).random().toLong())
        return when (id) {
            0L -> {
                NetworkResponse.ServerError(null, 400)
            }
            111111L -> {
                NetworkResponse.ServerError(null, 404)
            }
            else -> {
                NetworkResponse.Success(mockResponse(), null, 200)
            }
        }
    }

    private fun mockResponse(): CurrentResponse {
        return CurrentResponse(
            coord = CurrentResponse.Coord(0.0, 0.0),
            weather = listOf(
                CurrentResponse.Weather(
                    id = 1,
                    main = "main",
                    description = "description",
                    icon = "icon"
                )
            ),
            base = "base",
            main = CurrentResponse.Main(
                temp = 0.0,
                feelsLike = 0.0,
                tempMin = 0.0,
                tempMax = 0.0,
                pressure = 1000,
                humidity = 50
            ),
            visibility = 10000,
            wind = null,
            rain = null,
            snow = null,
            clouds = CurrentResponse.Clouds(
                50
            ),
            dt = 10,
            sys = CurrentResponse.Sys(
                country = "ES",
                sunrise = 1,
                sunset = 2
            ),
            timezone = 7200,
            id = 687836257,
            name = "gran alacant",
            cod = 200
        )
    }
}