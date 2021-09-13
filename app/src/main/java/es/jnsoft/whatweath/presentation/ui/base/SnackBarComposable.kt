package es.jnsoft.whatweath.presentation.ui.base

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun SnackBarComposable(
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
}