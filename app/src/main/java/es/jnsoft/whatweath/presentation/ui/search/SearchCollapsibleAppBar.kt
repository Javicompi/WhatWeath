package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import es.jnsoft.whatweath.R

@ExperimentalAnimationApi
@Composable
fun SearchCollapsibleAppBar(
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shown: Boolean
) {
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(TextFieldDefaults.MinHeight),
            color = MaterialTheme.colors.primarySurface,
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface),
            shape = RoundedCornerShape(
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            )
        ) {
            val focusManager = LocalFocusManager.current
            val searchText = remember { mutableStateOf("") }
            TextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearchClick(searchText.value)
                    }
                ),
                label = {
                    Text(
                        text = stringResource(id = R.string.screen_search),
                        color = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
                    )
                },
                trailingIcon = {
                    AnimatedContent(targetState = isLoading) { showProgressIndicator ->
                        if (showProgressIndicator) {
                            CircularProgressIndicator(
                                color = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        focusManager.clearFocus()
                                        onSearchClick(searchText.value)
                                    }
                                    .size(24.dp),
                                tint = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
                )
            )
        }
    }
}