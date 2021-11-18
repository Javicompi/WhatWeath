package es.jnsoft.whatweath.presentation.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.jnsoft.whatweath.presentation.model.CurrentPresentation

@Composable
fun DrawerItem(
    current: CurrentPresentation,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (current.icon) {
        "01d" -> { Color(0xFF5BC5F2) }
        "02d" -> { Color(0xFF56B7E0) }
        "03d" -> { Color(0xFF50A9CE) }
        "04d" -> { Color(0xFF499ABB) }
        "09d" -> { Color(0xFF397A94) }
        "10d" -> { Color(0xFF418AA8) }
        "11d" -> { Color(0xFF397A94) }
        "13d" -> { Color(0xFF397A94) }
        "50d" -> { Color(0xFF397A94) }
        "01n" -> { Color(0xFF575756) }
        "02n" -> { Color(0xFF4A4A49) }
        "03n" -> { Color(0xFF3C3C3B) }
        "04n" -> { Color(0xFF282727) }
        else -> { Color(0xFF000000) }
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = backgroundColor,
        elevation = if (isSelected) 0.dp else 4.dp,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = current.name,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Text(
                text = current.temp,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = current.description,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview
@Composable
fun PreviewDrawerItem() {
    DrawerItem(
        current = mockCurrentPresentation(),
        isSelected = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    )
}

private fun mockCurrentPresentation(): CurrentPresentation {
    return CurrentPresentation(
        clouds = "80%",
        country = "ES",
        deltaTime = 1637230028000L,
        description = "Muy nuboso",
        humidity = "93%",
        icon = "04d",
        id = 6697298L,
        lat = 38.2246,
        lon = -0.5192,
        name = "Gran Alacant",
        pressure = "1025 hpa",
        rain = 0.25,
        rainText = "0.25mm",
        snow = 0.0,
        snowText = "0.0mm",
        sunrise = 1637218002000L,
        sunriseText = "07:40",
        sunset = 1637254088000L,
        sunsetText = "17:39",
        daytimeDuration = "10:01",
        temp = "14º",
        tempFeelsLike = "14º",
        timeZone = 3600,
        visibility = "8kms",
        windDegrees = 53,
        windDegreesText = "E",
        windSpeed = "4kmh"
    )
}