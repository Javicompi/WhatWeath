package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.ui.search.NewSearchViewModel.*
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: NewSearchViewModel,
    navController: NavHostController
) {
    val events = viewModel.eventsFlow.collectAsState(initial = null)
    val searchResult = viewModel.presentationData.collectAsState(initial = null).value
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column {
            SearchHeader(
                state = viewModel.presentationData.collectAsState(initial = null).value,
                onSearchClick = { viewModel.findByName(name = it) },
                modifier = Modifier
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (searchResult) {
                    is Result.Success -> {
                        SearchElement(
                            current = searchResult.value,
                            onClick = {
                                // TODO
                            }
                        )
                    }
                    is Result.Loading -> {
                        SearchProgressIndicator()
                    }
                    else -> {
                        SearchEmptyMessage()
                    }
                }
            }
        }
        val resources = LocalContext.current.resources
        val event = events.value
        LaunchedEffect(event) {
            when (event) {
                /*is NavigateTo -> {
                    navController.navigate(event.destination)
                }*/
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
private fun SearchHeader(
    state: Result<CurrentPresentation>?,
    onSearchClick: (String) -> Unit,
    modifier: Modifier
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        val searchText = remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.search_text_field_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.weight(1f),
            enabled = state != Result.Loading
        )
        SearchButton(
            modifier = Modifier.padding(top = 8.dp, start = 24.dp),
            onSearchClick = {
                focusManager.clearFocus()
                onSearchClick(searchText.value)
            },
            buttonState = if (state is Result.Loading) {
                SearchButtonState.SEARCHING
            } else {
                SearchButtonState.ENABLED
            }
        )
    }
}

@Composable
private fun SearchEmptyMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 24.dp)
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