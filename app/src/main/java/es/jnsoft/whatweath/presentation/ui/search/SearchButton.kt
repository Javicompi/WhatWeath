package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.whatweath.R

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    buttonState: SearchButtonState = SearchButtonState.ENABLED
) {
    Button(
        onClick = onSearchClick,
        modifier = modifier,
        enabled = buttonState != SearchButtonState.SEARCHING
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            if (buttonState == SearchButtonState.SEARCHING) {
                CircularProgressIndicator(
                    color = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(id = R.string.screen_search),
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 14.dp)
            )
        }
    }
}

enum class SearchButtonState {
    ENABLED, SEARCHING
}