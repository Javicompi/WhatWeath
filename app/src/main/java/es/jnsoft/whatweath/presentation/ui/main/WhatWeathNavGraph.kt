package es.jnsoft.whatweath.presentation.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.jnsoft.whatweath.presentation.ui.current.CurrentScreen
import es.jnsoft.whatweath.presentation.ui.current.CurrentViewModel
import es.jnsoft.whatweath.presentation.ui.days.DaysScreen
import es.jnsoft.whatweath.presentation.ui.days.DaysViewModel
import es.jnsoft.whatweath.presentation.ui.search.SearchScreen
import es.jnsoft.whatweath.presentation.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
                searchViewModel
            )
        }
    }
}