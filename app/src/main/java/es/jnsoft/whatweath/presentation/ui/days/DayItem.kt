package es.jnsoft.whatweath.presentation.ui.days

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.DailyPresentation
import es.jnsoft.whatweath.presentation.ui.common.WeatherElementIcon
import es.jnsoft.whatweath.presentation.ui.common.WeatherElementSunriseSunset

@ExperimentalAnimationApi
@Composable
fun DayItem(
    daily: DailyPresentation,
    weatherIconId: Int,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(12.dp)
        ) {
            val (tempMaxRef, tempMinRef, tempBarRef, expandIconRef, weatherIconRef, weekDayRef) = createRefs()
            val rotation: Float by animateFloatAsState(if (expanded) 180.0f else 0.0f)
            Text(
                text = daily.dayText,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.constrainAs(weekDayRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )
            Text(
                text = daily.tempMax,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.constrainAs(tempMaxRef) {
                    top.linkTo(tempBarRef.top)
                    start.linkTo(parent.start)
                }
            )
            Text(
                text = "/",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.constrainAs(tempBarRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(tempMaxRef.end)
                }
            )
            Text(
                text = daily.tempMin,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.constrainAs(tempMinRef) {
                    bottom.linkTo(tempBarRef.bottom)
                    start.linkTo(tempBarRef.end)
                }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_expand),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .constrainAs(expandIconRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .rotate(rotation)
            )
            Icon(
                painter = painterResource(id = weatherIconId),
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
                    .constrainAs(weatherIconRef) {
                        top.linkTo(parent.top)
                        end.linkTo(expandIconRef.start)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    WeatherElementIcon(
                        iconRes = R.drawable.ic_uvi,
                        iconTitle = R.string.daily_uvi_title,
                        iconDescription = stringResource(
                            when {
                                daily.uvi.toInt() <= 2 -> R.string.daily_uvi_low
                                daily.uvi.toInt() <= 5 -> R.string.daily_uvi_moderate
                                daily.uvi.toInt() <= 7 -> R.string.daily_uvi_high
                                daily.uvi.toInt() <= 10 -> R.string.daily_uvi_very_high
                                else -> R.string.daily_uvi_extreme
                            }
                        ),
                        modifier = Modifier.weight(0.5f)
                    )
                    WeatherElementIcon(
                        iconRes = R.drawable.ic_cloud,
                        iconTitle = R.string.search_clouds_title,
                        iconDescription = daily.clouds,
                        modifier = Modifier.weight(0.5f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    when {
                        daily.snow > 0.0 -> {
                            WeatherElementIcon(
                                iconRes = R.drawable.ic_snow,
                                iconTitle = R.string.search_snow_title,
                                iconDescription = daily.snowText,
                                modifier = Modifier.weight(0.5f)
                            )
                            WeatherElementIcon(
                                iconRes = R.drawable.ic_humidity,
                                iconTitle = R.string.current_probability_title,
                                iconDescription = "${daily.pop} %",
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                        daily.rain > 0.0 -> {
                            WeatherElementIcon(
                                iconRes = R.drawable.ic_rain,
                                iconTitle = R.string.search_rain_title,
                                iconDescription = daily.rainText,
                                modifier = Modifier.weight(0.5f)
                            )
                            WeatherElementIcon(
                                iconRes = R.drawable.ic_humidity,
                                iconTitle = R.string.current_probability_title,
                                iconDescription = "${daily.pop} %",
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                        else -> {
                            WeatherElementIcon(
                                iconRes = R.drawable.ic_humidity,
                                iconTitle = R.string.search_humidity_title,
                                iconDescription = daily.humidity,
                                modifier = Modifier.weight(0.5f)
                            )
                            WeatherElementIcon(
                                iconRes = R.drawable.ic_pressure,
                                iconTitle = R.string.search_pressure_title,
                                iconDescription = daily.pressure,
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    WeatherElementIcon(
                        iconRes = R.drawable.ic_wind,
                        iconTitle = R.string.search_wind_speed_title,
                        iconDescription = daily.windSpeedText,
                        modifier = Modifier.weight(0.5f)
                    )
                    WeatherElementIcon(
                        iconRes = R.drawable.ic_degrees,
                        iconTitle = R.string.search_direction_title,
                        iconDescription = daily.windDegreesText,
                        iconDegrees = daily.windDegrees,
                        modifier = Modifier.weight(0.5f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    WeatherElementSunriseSunset(
                        sunrise = daily.sunriseText,
                        sunset = daily.sunsetText,
                        sunDuration = daily.dayTimeDuration
                    )
                }
            }
        }
    }
}