package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation

@Composable
fun SearchBottomSheet(
    current: CurrentPresentation?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
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
            SearchElementIcon(
                iconRes = R.drawable.ic_temp,
                iconTitle = R.string.search_temp_title,
                iconDescription = current?.temp ?: "~~",
                modifier = Modifier.weight(0.5f)
            )
            SearchElementIcon(
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
                    SearchElementIcon(
                        iconRes = R.drawable.ic_snow,
                        iconTitle = R.string.search_snow_title,
                        iconDescription = current?.snowText ?: "~~",
                        modifier = Modifier.weight(0.5f)
                    )
                }
                current?.rain ?: 0.0 > 0.0 -> {
                    SearchElementIcon(
                        iconRes = R.drawable.ic_rain,
                        iconTitle = R.string.search_rain_title,
                        iconDescription = current?.rainText ?: "~~",
                        modifier = Modifier.weight(0.5f)
                    )
                }
                else -> {
                    SearchElementIcon(
                        iconRes = R.drawable.ic_humidity,
                        iconTitle = R.string.search_humidity_title,
                        iconDescription = current?.humidity ?: "~~",
                        modifier = Modifier.weight(0.5f)
                    )
                }
            }
            SearchElementIcon(
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
            SearchElementIcon(
                iconRes = R.drawable.ic_wind,
                iconTitle = R.string.search_wind_title,
                iconDescription = current?.windSpeed ?: "~~",
                modifier = Modifier.weight(0.5f)
            )
            SearchElementIcon(
                iconRes = R.drawable.ic_degrees,
                iconTitle = R.string.search_direction_title,
                iconDescription = current?.windDegreesText ?: "~~",
                iconDegrees = current?.windDegrees ?: 0,
                modifier = Modifier.weight(0.5f)
            )
        }
        Spacer(modifier = Modifier.height(320.dp))
    }
}

@Composable
private fun SearchElementIcon(
    modifier: Modifier = Modifier,
    iconRes: Int,
    iconTitle: Int,
    iconDescription: String,
    iconDegrees: Int = 0
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = Color.White.copy(alpha = 0.5f),
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

@Preview
@Composable
fun PreviewSearchBottomSheet() {
    SearchBottomSheet(current = null)
}