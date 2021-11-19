package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.HourlyPresentation
import es.jnsoft.whatweath.utils.DrawableLoader
import es.jnsoft.whatweath.utils.DrawableType

@Composable
fun CurrentHoursForecast(
    hourlies: List<HourlyPresentation>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.search_message_hourlies_forecast, hourlies.size),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 32.dp, top = 16.dp)
        )
        val tempEntries = hourlies.map { hourly ->
            ChartDataEntry(
                entryValue = hourly.temp.toDouble(),
                entryTitle = hourly.tempText
            )
        }
        val windEntries = hourlies.map { hourly ->
            ChartDataEntry(
                entryValue = hourly.windSpeed,
                entryTitle = hourly.windSpeedText
            )
        }
        val precipitationEntries = hourlies.map { hourly ->
            when {
                hourly.snow > 0.0 -> {
                    ChartDataEntry(
                        entryValue = hourly.snow,
                        entryTitle = hourly.snowText
                    )
                }
                hourly.rain > 0.0 -> {
                    ChartDataEntry(
                        entryValue = hourly.rain,
                        entryTitle = hourly.rainText
                    )
                }
                else -> {
                    ChartDataEntry(
                        entryValue = 0.0,
                        entryTitle = hourly.rainText
                    )
                }
            }
        }
        val forecastScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(forecastScrollState)
        ) {
            Column(
                modifier = Modifier
                    .width(1280.dp)
            ) {
                LineChart(
                    entries = tempEntries,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    hourlies.forEach { hourly ->
                        Text(
                            text = hourly.timeText,
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    hourlies.forEach { hourly ->
                        Icon(
                            painter = painterResource(
                                id = DrawableLoader.loadDrawable(
                                    resourceName = hourly.icon,
                                    drawableType = DrawableType.ICONELEMENT,
                                    context = context
                                )
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 24.dp, start = 48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.search_wind_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    hourlies.forEach { hourly ->
                        Icon(
                            painter = painterResource(id = R.drawable.ic_degrees),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .rotate(degrees = hourly.windDegrees.toFloat()),
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
                BarChart(
                    entries = windEntries,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(top = 24.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 24.dp, start = 48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.current_precipitation_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    hourlies.forEach { hourly ->
                        Text(
                            text = hourly.timeText,
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 16.dp, start = 72.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.current_probability_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    hourlies.forEach { hourly ->
                        Text(
                            text = "${hourly.pop} %",
                            fontSize = 12.sp,
                            color = MaterialTheme.colors.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(48.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 16.dp, start = 72.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.current_amount_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                FilledChart(
                    entries = precipitationEntries,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(top = 24.dp),
                    contentColor = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}