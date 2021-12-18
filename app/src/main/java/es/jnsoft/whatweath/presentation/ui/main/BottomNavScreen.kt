package es.jnsoft.whatweath.presentation.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import es.jnsoft.whatweath.R

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Current : BottomNavScreen("current", R.string.screen_current, Icons.Filled.Place)
    object Days : BottomNavScreen("days", R.string.screen_week, Icons.Filled.List)
    object Search : BottomNavScreen("search", R.string.screen_search, Icons.Filled.Search)
}