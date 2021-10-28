package es.jnsoft.framework.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourlies")
data class HourlyEntity(
    @ColumnInfo(name = "city_id")
    val cityId: Long,
    @ColumnInfo(name = "clouds")
    val clouds: Int,
    @ColumnInfo(name = "delta_time")
    val deltaTime: Long,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "dew_point")
    val dewPoint: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "icon")
    val icon: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "pop")
    val pop: Double,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "rain")
    val rain: Double,
    @ColumnInfo(name = "snow")
    val snow: Double,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "temp_feels_like")
    val tempFeelsLike: Double,
    @ColumnInfo(name = "time_zone")
    val timeZone: Int,
    @ColumnInfo(name = "uvi")
    val uvi: Double,
    @ColumnInfo(name = "visibility")
    val visibility: Int,
    @ColumnInfo(name = "wind_degrees")
    val windDegrees: Int,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double
)
