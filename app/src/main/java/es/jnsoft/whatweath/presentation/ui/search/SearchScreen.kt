package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavHostController
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.ui.common.CurrentBottomSheet
import es.jnsoft.whatweath.presentation.ui.main.BottomNavScreen
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel.*
import es.jnsoft.whatweath.presentation.ui.theme.WhatWeathTheme
import es.jnsoft.whatweath.utils.DrawableLoader
import es.jnsoft.whatweath.utils.DrawableType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController
) {
    val events = viewModel.eventsFlow.collectAsState(initial = null)
    val searchCurrentResult = viewModel.currentPresentation.collectAsState(initial = null)
    val searchHourlyResult = viewModel.hourlyPresentation.collectAsState(initial = Result.Loading)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val isCollapsed = scaffoldState.bottomSheetState.targetValue.ordinal == 0
    val bottomSheetColor = animateColorAsState(if (isCollapsed) {
        MaterialTheme.colors.surface.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colors.surface
    })
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        when (val result = searchCurrentResult.value) {
            is Result.Loading -> {
                SearchProgressIndicator()
            }
            is Result.Success -> {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        CurrentBottomSheet(
                            current = result.value,
                            hourlies = searchHourlyResult.value
                        )
                    },
                    sheetPeekHeight = 276.dp,
                    sheetBackgroundColor = bottomSheetColor.value,
                    //sheetBackgroundColor = MaterialTheme.colors.surface.copy(alpha = bottomSheetColor.value),
                    sheetElevation = 0.dp,
                    sheetShape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                ) {
                    SearchBackDropBackground(
                        current = result.value,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                SearchFloatingActionButton(
                    collapsed = isCollapsed,
                    onClick = {
                        viewModel.saveData()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp)
                )
            }
            else -> {
                SearchEmptyMessage()
            }
        }
        SearchCollapsibleAppBar(
            onSearchClick = { viewModel.findByName(it) },
            isLoading = searchCurrentResult.value is Result.Loading,
            shown = isCollapsed
        )
        val resources = LocalContext.current.resources
        val event = events.value
        LaunchedEffect(event) {
            when (event) {
                is Event.NavigateToCurrent -> {
                    navController.navigate(BottomNavScreen.Current.route)
                }
                is Event.ShowSnackbarResource -> {
                    val message = event.resource
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = resources.getString(message)
                        )
                    }
                }
                is Event.ShowSnackbarString -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = (events.value as Event.ShowSnackbarString).message
                        )
                    }
                }
                else -> return@LaunchedEffect
            }
            viewModel.sendEvent(Event.Clean)
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SearchBackDropBackground(
    current: CurrentPresentation?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Surface {
        Box(modifier = modifier) {
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
                color = current?.let {
                    if (it.icon.contains("n")) {
                        Color.White
                    } else {
                        Color.Black
                    }
                } ?: MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(top = 72.dp, end = 24.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun SearchFloatingActionButton(
    collapsed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
            AnimatedVisibility(visible = collapsed) {
                Text(
                    text = stringResource(id = R.string.search_fab_save),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchEmptyMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
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

@Composable
private fun SearchProgressIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewCollapsibleAppBarDefault() {
    WhatWeathTheme(darkTheme = true) {
        SearchCollapsibleAppBar(onSearchClick = {}, shown = false)
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewCollapsibleAppBarLoading() {
    SearchCollapsibleAppBar(isLoading = true, onSearchClick = {}, shown = false)
}