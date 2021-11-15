package es.jnsoft.whatweath.presentation.ui.current

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.ui.common.AnimatedFloatingActionButton
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun CurrentScreen(
    viewModel: CurrentViewModel
) {
    val currentResult by viewModel.currentPresentation.collectAsState(initial = Result.Failure(""))
    val hourlyResult by viewModel.hourlyPresentation.collectAsState(initial = Result.Loading)
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = SnackbarHostState()
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val isCollapsed = bottomSheetScaffoldState.bottomSheetState.targetValue.ordinal == 0
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedFloatingActionButton(
                text = stringResource(id = R.string.current_delete),
                onClick = { viewModel.deleteData() },
                show = currentResult is Result.Success,
                collapsed = isCollapsed
            )
        }
    ) { paddingValues ->
        CurrentContent(
            scaffoldState = bottomSheetScaffoldState,
            currentValue = currentResult,
            hourliesValue = hourlyResult,
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}