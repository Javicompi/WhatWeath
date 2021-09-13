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
import es.jnsoft.whatweath.presentation.ui.search.SearchScreen
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Current : Screen("current", R.string.screen_current, Icons.Filled.Home)
    object Hours : Screen("hours", R.string.screen_hours, Icons.Filled.Info)
    object Days : Screen("days", R.string.screen_days, Icons.Filled.Clear)
    object Search : Screen("search", R.string.screen_search, Icons.Filled.Search)
}

@Composable
fun WhatWeathNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Current.route,
        modifier = modifier
    ) {
        composable(Screen.Current.route) {
            CurrentScreen()
        }
        composable(Screen.Hours.route) {
            HoursScreen()
        }
        composable(Screen.Days.route) {
            DaysScreen()
        }
        composable(Screen.Search.route) {
            val searchViewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                dataFlow = searchViewModel.presentation,
                result = searchViewModel.domainData,
                onSearchClick = { searchViewModel.findByName(name = it) },
                errorResourceFlow = searchViewModel.errorResourceFlow,
                errorStringFlow = searchViewModel.errorStringFlow
            )
        }
    }
}