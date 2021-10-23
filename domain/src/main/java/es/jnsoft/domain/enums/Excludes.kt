package es.jnsoft.domain.enums

enum class Excludes(val value: String) {

    HOURLY("current,minutely,daily,alerts"),
    DAILY("current,minutely,hourly,alerts")
}