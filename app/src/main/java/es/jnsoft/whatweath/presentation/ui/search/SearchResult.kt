package es.jnsoft.whatweath.presentation.ui.search

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController

@Composable
fun SearchResultScreen(
    viewModel: SearchViewModel,
    navController: NavHostController
) {
    val current = viewModel.presentation.collectAsState(initial = null).value
    Text(
        text = current?.name ?: "Nothing to show"
    )
}