package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.whatweath.presentation.model.CurrentPresentation

@Composable
fun SearchElement(
    current: CurrentPresentation?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box {
            Text(
                text = (current?.let { current ->
                    if (current.name.isNotEmpty() && current.country.isNotEmpty()) {
                        current.name + ", " + current.country
                    } else {
                        current.name
                    }
                } ?: "Gran Alacant, ES"),
                textAlign = TextAlign.End,
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(all = 24.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.BottomStart)
            ) {
                Text(text = "Content start")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.BottomEnd)
            ) {
                Text(text = "Content end")
            }
        }
    }
}