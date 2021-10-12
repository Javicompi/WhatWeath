package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
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
    Box(
        modifier = modifier
            .clickable { onClick() }
    ) {
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
                .padding(all = 24.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchElementIcon(
                iconRes = R.drawable.ic_temp,
                iconTitle = R.string.search_temp_title,
                iconDescription = current?.temp ?: "~~"
            )
            when {
                current?.snow ?: 0.0 > 0.0 -> {
                    SearchElementIcon(
                        iconRes = R.drawable.ic_snow,
                        iconTitle = R.string.search_snow_title,
                        iconDescription = current?.snowText ?: "~~"
                    )
                }
                current?.rain ?: 0.0 > 0.0 -> {
                    SearchElementIcon(
                        iconRes = R.drawable.ic_rain,
                        iconTitle = R.string.search_rain_title,
                        iconDescription = current?.rainText ?: "~~"
                    )
                }
                else -> {
                    SearchElementIcon(
                        iconRes = R.drawable.ic_humidity,
                        iconTitle = R.string.search_humidity_title,
                        iconDescription = current?.humidity ?: "~~"
                    )
                }
            }
            SearchElementIcon(
                iconRes = R.drawable.ic_wind,
                iconTitle = R.string.search_wind_title,
                iconDescription = current?.windSpeed ?: "~~"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchElementIcon(
                iconRes = R.drawable.ic_cloud,
                iconTitle = R.string.search_clouds_title,
                iconDescription = current?.clouds ?: "~~"
            )
            SearchElementIcon(
                iconRes = R.drawable.ic_pressure,
                iconTitle = R.string.search_pressure_title,
                iconDescription = current?.pressure ?: "~~"
            )
            SearchElementIcon(
                iconRes = R.drawable.ic_degrees,
                iconTitle = R.string.search_direction_title,
                iconDescription = current?.windDegreesText ?: "~~",
                iconDegrees = current?.windDegrees ?: 0
            )
        }
    }
    /*Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(onClick = onClick),
        backgroundColor = Color.LightGray
    ) {


    }*/
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
            .fillMaxWidth()
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