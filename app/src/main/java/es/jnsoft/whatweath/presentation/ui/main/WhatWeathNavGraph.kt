package es.jnsoft.whatweath.presentation.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.ui.current.CurrentScreen
import es.jnsoft.whatweath.presentation.ui.current.CurrentViewModel
import es.jnsoft.whatweath.presentation.ui.days.DaysScreen
import es.jnsoft.whatweath.presentation.ui.days.DaysViewModel
import es.jnsoft.whatweath.presentation.ui.search.SearchScreen
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Current : BottomNavScreen("current", R.string.screen_current, Icons.Filled.Place)
    object Days : BottomNavScreen("days", R.string.screen_week, Icons.Filled.List)
    object Search : BottomNavScreen("search", R.string.screen_search, Icons.Filled.Search)
}

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
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
            val currentViewModel: CurrentViewModel = hiltViewModel()
            CurrentScreen(currentViewModel)
        }
        composable(BottomNavScreen.Days.route) {
            val daysViewModel: DaysViewModel = hiltViewModel()
            DaysScreen(daysViewModel)
        }
        composable(BottomNavScreen.Search.route) {
            val searchViewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                searchViewModel,
                navController
            )
        }
    }
}