package es.jnsoft.framework.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailies")
data class DailyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
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
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "moon_phase")
    val moonPhase: Int,
    @ColumnInfo(name = "pop")
    val pop: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "rain")
    val rain: Double,
    @ColumnInfo(name = "snow")
    val snow: Double,
    @ColumnInfo(name = "sunrise")
    val sunrise: Long,
    @ColumnInfo(name = "sunset")
    val sunset: Long,
    @ColumnInfo(name = "temp_max")
    val tempMax: Double,
    @ColumnInfo(name = "temp_min")
    val tempMin: Double,
    @ColumnInfo(name = "time_zone")
    val timeZone: Int,
    @ColumnInfo(name = "uvi")
    val uvi: Double,
    @ColumnInfo(name = "wind_degrees")
    val windDegrees: Int,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double
)
