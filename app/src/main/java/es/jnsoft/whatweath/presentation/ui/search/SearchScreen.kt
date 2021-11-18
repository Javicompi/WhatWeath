package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.ui.common.AnimatedFloatingActionButton
import es.jnsoft.whatweath.presentation.ui.current.CurrentContent
import es.jnsoft.whatweath.presentation.ui.main.BottomNavScreen
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel.*
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
    val searchCurrentResult by viewModel.currentPresentation.collectAsState(
        initial = Result.Failure("")
    )
    val searchHourlyResult by viewModel.hourlyPresentation.collectAsState(initial = Result.Loading)
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
        snackbarHostState = SnackbarHostState()
    )
    val isCollapsed = bottomSheetScaffoldState.bottomSheetState.isCollapsed
    val resources = LocalContext.current.resources
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedFloatingActionButton(
                text = stringResource(id = R.string.search_fab_save),
                onClick = { viewModel.saveData() },
                show = searchCurrentResult is Result.Success,
                collapsed = isCollapsed
            )
        },
        snackbarHost = {
            when (val event = events.value) {
                is Event.NavigateToCurrent -> {
                    navController.navigate(BottomNavScreen.Current.route)
                }
                is Event.ShowSnackbarResource -> {
                    val message = event.resource
                    coroutineScope.launch {
                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                            message = resources.getString(message)
                        )
                    }
                }
                is Event.ShowSnackbarString -> {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                            message = (events.value as Event.ShowSnackbarString).message
                        )
                    }
                }
                else -> return@Scaffold
            }
            viewModel.sendEvent(Event.Clean)
        }
    ) { paddingValues ->
        CurrentContent(
            scaffoldState = bottomSheetScaffoldState,
            currentValue = searchCurrentResult,
            hourliesValue = searchHourlyResult,
            modifier = Modifier.padding(paddingValues)
        )
        SearchCollapsibleAppBar(
            onSearchClick = { viewModel.findByName(it) },
            isLoading = searchCurrentResult is Result.Loading,
            shown = isCollapsed
        )
    }
}