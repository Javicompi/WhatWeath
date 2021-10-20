package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.utils.DrawableLoader
import es.jnsoft.whatweath.utils.DrawableType

@Composable
fun SearchElement(
    current: CurrentPresentation?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(
                id = DrawableLoader.loadDrawable(
                    current?.icon ?: "01d",
                    DrawableType.FULLSCREEN,
                    context
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = (current?.let { current ->
                if (current.name.isNotEmpty() && current.country.isNotEmpty()) {
                    current.name + ", " + current.country
                } else {
                    current.name
                }
            } ?: "Unknown location"),
            textAlign = TextAlign.End,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(top = 72.dp, end = 24.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .weight(weight = 0.5f)
                    .fillMaxWidth()
                    .background(color = Color.Red)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
            Row(modifier = Modifier.fillMaxWidth()) {
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
            Row(modifier = Modifier.fillMaxWidth()) {
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
            Text(
                text = "This is a test",
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "This is another test",
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "And yet another",
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
                shape = RoundedCornerShape(4.dp)
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

@Preview(showBackground = true, backgroundColor = 0xFFF4F4F4, widthDp = 200)
@Composable
private fun SearchElementIconPreview() {
    SearchElementIcon(
        iconRes = R.drawable.ic_temp,
        iconTitle = R.string.search_temp_title,
        iconDescription = "22 ºC"
    )
}