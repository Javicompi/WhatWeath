package es.jnsoft.domain.model

sealed class Result<out T> {

    object Loading : Result<Nothing>()
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val message: String) : Result<Nothing>()
}