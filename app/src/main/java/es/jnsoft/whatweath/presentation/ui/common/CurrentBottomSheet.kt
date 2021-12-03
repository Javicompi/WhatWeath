package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.model.HourlyPresentation

@Composable
fun CurrentBottomSheet(
    current: CurrentPresentation?,
    hourlies: Result<List<HourlyPresentation>>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .width(48.dp)
                    .height(4.dp),
                shape = RoundedCornerShape(50),
                color = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            CurrentElementIcon(
                iconRes = R.drawable.ic_temp,
                iconTitle = R.string.current_temp_feels_like_title,
                iconDescription = current?.tempFeelsLike ?: "~~",
                modifier = Modifier.weight(0.5f)
            )
            CurrentElementIcon(
                iconRes = R.drawable.ic_cloud,
                iconTitle = R.string.search_clouds_title,
                iconDescription = current?.clouds ?: "~~",
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            when {
                current?.snow ?: 0.0 > 0.0 -> {
                    CurrentElementIcon(
                        iconRes = R.drawable.ic_snow,
                        iconTitle = R.string.search_snow_title,
                        iconDescription = current?.snowText ?: "~~",
                        modifier = Modifier.weight(0.5f)
                    )
                }
                current?.rain ?: 0.0 > 0.0 -> {
                    CurrentElementIcon(
                        iconRes = R.drawable.ic_rain,
                        iconTitle = R.string.search_rain_title,
                        iconDescription = current?.rainText ?: "~~",
                        modifier = Modifier.weight(0.5f)
                    )
                }
                else -> {
                    CurrentElementIcon(
                        iconRes = R.drawable.ic_humidity,
                        iconTitle = R.string.search_humidity_title,
                        iconDescription = current?.humidity ?: "~~",
                        modifier = Modifier.weight(0.5f)
                    )
                }
            }
            CurrentElementIcon(
                iconRes = R.drawable.ic_pressure,
                iconTitle = R.string.search_pressure_title,
                iconDescription = current?.pressure ?: "~~",
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            CurrentElementIcon(
                iconRes = R.drawable.ic_wind,
                iconTitle = R.string.search_wind_speed_title,
                iconDescription = current?.windSpeed ?: "~~",
                modifier = Modifier.weight(0.5f)
            )
            CurrentElementIcon(
                iconRes = R.drawable.ic_degrees,
                iconTitle = R.string.search_direction_title,
                iconDescription = current?.windDegreesText ?: "~~",
                iconDegrees = current?.windDegrees ?: 0,
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            CurrentElementSunriseSunset(
                sunrise = current?.sunriseText ?: "",
                sunset = current?.sunsetText ?: "",
                sunDuration = current?.daytimeDuration ?: ""
            )
        }
        when (hourlies) {
            is Result.Loading -> {
                SearchProgressIndicator()
            }
            is Result.Failure -> {
                SearchEmptyMessage()
            }
            is Result.Success -> {
                CurrentHoursForecast(
                    hourlies = hourlies.value,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CurrentElementIcon(
    iconRes: Int,
    iconTitle: Int,
    iconDescription: String,
    modifier: Modifier = Modifier,
    iconDegrees: Int = 0
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .rotate(iconDegrees.toFloat())
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(id = iconTitle),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(
                    text = iconDescription,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun CurrentElementSunriseSunset(
    sunrise: String,
    sunset: String,
    sunDuration: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Column {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunrise),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunline),
                    contentDescription = null,
                    modifier = Modifier
                        .height(48.dp)
                        .weight(3f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_sunset),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .weight(1f)
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.current_sunrise_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = R.string.current_daylight_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = stringResource(id = R.string.current_sunset_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
            Row {
                Text(
                    text = sunrise,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = sunDuration,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(3f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = sunset,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun SearchEmptyMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(start = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = (stringResource(id = R.string.search_message_empty_hourlies_title)),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = (stringResource(id = R.string.search_message_empty_hourlies_message)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun SearchProgressIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun PreviewSearchBottomSheet() {
    CurrentBottomSheet(current = null, hourlies = Result.Failure(""))
}

@Preview(widthDp = 360)
@Composable
fun PreviewSearchElementSunriseSunset() {
    CurrentElementSunriseSunset(sunrise = "7:15", sunset = "18.21", sunDuration = "11:06")
}