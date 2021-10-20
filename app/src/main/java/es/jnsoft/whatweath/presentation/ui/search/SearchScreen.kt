package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import es.jnsoft.whatweath.presentation.ui.main.BottomNavScreen
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel.*
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController
) {
    val events = viewModel.eventsFlow.collectAsState(initial = null)
    val searchResult = viewModel.presentationData.collectAsState(initial = null)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchCollapsingLayout(
            state = searchResult.value,
            onSearchClick = { viewModel.findByName(it) },
            modifier = Modifier.fillMaxSize()
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