package es.jnsoft.whatweath.presentation.ui.current

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.ui.common.AnimatedFloatingActionButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun CurrentScreen(
    viewModel: CurrentViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val currentResult by viewModel.currentPresentation.collectAsState(initial = Result.Failure(""))
    val hourlyResult by viewModel.hourlyPresentation.collectAsState(initial = Result.Loading)
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = SnackbarHostState()
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val isCollapsed = bottomSheetScaffoldState.bottomSheetState.isCollapsed
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedFloatingActionButton(
                text = stringResource(id = R.string.current_fab_delete),
                onClick = {
                    if (!isCollapsed) scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                    viewModel.deleteData()
                },
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
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onStart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}