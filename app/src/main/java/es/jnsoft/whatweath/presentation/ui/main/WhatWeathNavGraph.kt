package es.jnsoft.whatweath.presentation.ui.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.ui.CurrentScreen
import es.jnsoft.whatweath.presentation.ui.days.DaysScreen
import es.jnsoft.whatweath.presentation.ui.hours.HoursScreen
import es.jnsoft.whatweath.presentation.ui.search.NewSearchViewModel
import es.jnsoft.whatweath.presentation.ui.search.SearchScreen

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Current : BottomNavScreen("current", R.string.screen_current, Icons.Filled.Home)
    object Hours : BottomNavScreen("hours", R.string.screen_hours, Icons.Filled.Info)
    object Days : BottomNavScreen("days", R.string.screen_days, Icons.Filled.Clear)
    object Search : BottomNavScreen("search", R.string.screen_search, Icons.Filled.Search)
    object Result: BottomNavScreen("search", R.string.screen_search, Icons.Filled.Search)
}

@Composable
fun WhatWeathNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Current.route,
        modifier = modifier
    ) {
        composable(BottomNavScreen.Current.route) {
            CurrentScreen()
        }
        composable(BottomNavScreen.Hours.route) {
            HoursScreen()
        }
        composable(BottomNavScreen.Days.route) {
            DaysScreen()
        }
        composable(BottomNavScreen.Search.route) {
            val newSearchViewModel: NewSearchViewModel = hiltViewModel()
            SearchScreen(
                newSearchViewModel,
                navController
            )
        }
    }
}