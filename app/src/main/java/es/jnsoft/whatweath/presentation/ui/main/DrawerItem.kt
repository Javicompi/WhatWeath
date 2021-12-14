package es.jnsoft.whatweath.presentation.ui.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.utils.DrawableLoader
import es.jnsoft.whatweath.utils.DrawableType

@Composable
fun DrawerItem(
    current: CurrentPresentation,
    isSelected: Boolean,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val elevationDp: Dp by animateDpAsState(if (isSelected) 0.dp else 4.dp)
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = elevationDp,
        modifier = modifier
            .clickable { onItemClick() }
            .padding(
                if (isSelected) 6.dp else 0.dp
            )
    ) {
        Image(
            painter = painterResource(
                id = DrawableLoader.loadDrawable(
                    resourceName = current.icon,
                    drawableType = DrawableType.DRAWABLEELEMENT,
                    context = context
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = current.name,
                color = Color.White,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Text(
                text = current.temp,
                color = Color.White,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = current.description,
                color = Color.White,
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
        onItemClick = { },
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