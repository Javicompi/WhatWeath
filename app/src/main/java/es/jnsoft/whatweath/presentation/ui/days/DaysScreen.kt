package es.jnsoft.whatweath.presentation.ui.days

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.DailyPresentation
import es.jnsoft.whatweath.presentation.ui.common.AnimatedFloatingActionButton
import es.jnsoft.whatweath.presentation.ui.common.EmptyMessage
import es.jnsoft.whatweath.presentation.ui.common.ProgressIndicator
import es.jnsoft.whatweath.utils.DrawableLoader
import es.jnsoft.whatweath.utils.DrawableType
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun DaysScreen(
    viewModel: DaysViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val dailiesResult by viewModel.dailyPresentation.collectAsState(initial = Result.Loading)
    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()
    val shouldCollapse = lazyListState.firstVisibleItemIndex == 0
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AnimatedFloatingActionButton(
                text = stringResource(id = R.string.current_fab_delete),
                onClick = { viewModel.deleteData() },
                show = dailiesResult is Result.Success,
                collapsed = shouldCollapse
            )
        }
    ) {
        when(dailiesResult) {
            is Result.Failure -> {
                EmptyMessage(modifier = Modifier.fillMaxSize())
            }
            is Result.Loading -> {
                ProgressIndicator(modifier = Modifier.fillMaxSize())
            }
            is Result.Success -> {
                DailiesList(
                    dailies = (dailiesResult as Result.Success<List<DailyPresentation>>).value,
                    listState = lazyListState,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
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

@ExperimentalAnimationApi
@Composable
private fun DailiesList(
    dailies: List<DailyPresentation>,
    listState: LazyListState,
    modifier: Modifier
) {
    val context = LocalContext.current
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        state = listState,
        modifier = modifier
    ) {
        itemsIndexed(dailies) { index, daily ->
            val drawableId = DrawableLoader.loadDrawable(
                resourceName = daily.icon,
                drawableType = DrawableType.ICONELEMENT,
                context = context
            )
            DayItem(
                daily = daily,
                weatherIconId = drawableId,
                modifier = Modifier.fillMaxWidth()
            )
            if (index < dailies.lastIndex) {
                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp
                )
            }
        }
    }
}