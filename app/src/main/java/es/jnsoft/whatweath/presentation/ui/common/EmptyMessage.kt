package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.whatweath.R

@Composable
fun EmptyMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = (stringResource(id = R.string.search_message_empty_title)),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = (stringResource(id = R.string.search_message_empty_message)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}