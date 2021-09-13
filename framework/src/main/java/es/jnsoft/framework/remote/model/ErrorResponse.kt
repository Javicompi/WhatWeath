package es.jnsoft.framework.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "cod")
    val cod: Int,
    @Json(name = "message")
    val message: String
)