package es.jnsoft.framework.remote.service

import es.jnsoft.framework.BuildConfig

object ApiConstants {

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    const val API_KEY = BuildConfig.API_KEY

    const val EXCLUDES_HOURLY = "current,minutely,daily,alerts"

    const val EXCLUDES_DAILY = "current,minutely,hourly,alerts"
}