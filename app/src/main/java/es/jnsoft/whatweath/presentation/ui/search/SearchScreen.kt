package es.jnsoft.whatweath.presentation.ui.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    dataFlow: Flow<CurrentPresentation?>,
    result: Flow<Result<Current>?>,
    onSearchClick: (value: String) -> Unit,
    errorResourceFlow: Flow<Int>,
    errorStringFlow: Flow<String>
) {
    val searchText = rememberSaveable { mutableStateOf("") }
    val data = dataFlow.collectAsState(initial = null)
    val state = result.collectAsState(initial = Result.Success(null))
    val innerPadding = 24.dp
    var searchButtonState = remember { SearchButtonState.ENABLED }
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val errorResource = errorResourceFlow.collectAsState(initial = 0)
    val errorString = errorStringFlow.collectAsState(initial = "")
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(all = innerPadding)
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.search_text_field_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                enabled = state.value != Result.Loading
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SearchButton(
                    modifier = Modifier.padding(top = innerPadding),
                    onSearchClick = {
                        focusManager.clearFocus()
                        onSearchClick(searchText.value)
                    },
                    buttonState = searchButtonState,
                    onStateChanged = { searchButtonState = it }
                )
            }
            when (state.value) {
                is Result.Failure -> {
                    searchButtonState = SearchButtonState.ENABLED
                }
                Result.Loading -> {
                    searchButtonState = SearchButtonState.SEARCHING
                }
                is Result.Success -> {
                    searchButtonState = SearchButtonState.ENABLED
                    if (data.value != null) {
                        Log.d("MainScreen", "Show SearchElement")
                        Log.d("MainScreen", "data: ${data.value.toString()}")
                        SearchElement(
                            current = data.value,
                            modifier = Modifier.padding(top = innerPadding)
                        )
                    } else {
                        Log.d("MainScreen", "Show SearchEmptyMessage")
                        SearchEmptyMessage(modifier = Modifier.padding(top = innerPadding))
                    }
                }
            }
            if (errorResource.value > 0) {
                val resources = LocalContext.current.resources
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = resources.getString(errorResource.value),
                        duration = SnackbarDuration.Long
                    )
                }
            }
            if (errorString.value.isNotEmpty()) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = errorString.value,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SearchEmptyMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight(0.66f)
            .fillMaxWidth(),
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
            fontWeight = FontWeight.Light,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        dataFlow = flow { null },
        result = flow { null },
        onSearchClick = { },
        errorResourceFlow = flow { 0 },
        errorStringFlow = flow { "" }
    )
}