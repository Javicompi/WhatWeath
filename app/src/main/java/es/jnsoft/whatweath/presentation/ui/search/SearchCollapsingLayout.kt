package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlin.math.roundToInt

@Composable
fun SearchCollapsingLayout(
    state: Result<CurrentPresentation>?,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    collapsibleHeight: Dp = TextFieldDefaults.MinHeight
) {
    val collapsibleHeightPx = with(LocalDensity.current) { collapsibleHeight.roundToPx().toFloat() }
    val collapsibleOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = collapsibleOffsetHeightPx.value + delta
                collapsibleOffsetHeightPx.value = newOffset.coerceIn(-collapsibleHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier
            .nestedScroll(nestedScrollConnection),
        floatingActionButton = {
            if (state is Result.Success) {
                FloatingActionButton(
                    onClick = { /*TODO*/ }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
                        if (-collapsibleOffsetHeightPx.value.roundToInt() < 180) {
                            Text(
                                text = stringResource(id = R.string.search_fab_save),
                                modifier = Modifier.padding(start = 12.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        when (state) {
            is Result.Success -> {
                SearchElement(
                    current = state.value,
                    onClick = {
                        // TODO
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            is Result.Loading -> {
                SearchProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            else -> {
                SearchEmptyMessage(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        if (state is Result.Success) {
            SearchHeader(
                state = state,
                onSearchClick = { onSearchClick(it) },
                modifier = Modifier
                    .padding(innerPadding)
                    .offset { IntOffset(x = 0, y = collapsibleOffsetHeightPx.value.roundToInt()) }
            )
        } else {
            SearchHeader(
                state = state,
                onSearchClick = { onSearchClick(it) }
            )
        }
    }
}

@Composable
private fun SearchHeader(
    state: Result<CurrentPresentation>?,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val searchText = remember { mutableStateOf("") }
    TextField(
        value = searchText.value,
        onValueChange = { searchText.value = it },
        modifier = modifier
            .fillMaxWidth(),
        maxLines = 1,
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
        trailingIcon = {
            if (state is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    Modifier.clickable {
                        focusManager.clearFocus()
                        onSearchClick(searchText.value)
                    },
                    tint = Color.White
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            textColor = Color.White
        ),
        label = { Text(
            text = stringResource(id = R.string.screen_search),
            color = Color.White
        )}
    )
}

@Composable
private fun SearchEmptyMessage(
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
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = (stringResource(id = R.string.search_message_empty_message)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color.Black
        )
    }
}

@Composable
private fun SearchProgressIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun PreviewSearchCollapsingLayout(){
    SearchCollapsingLayout(
        state = null,
        onSearchClick = {}
    )
}