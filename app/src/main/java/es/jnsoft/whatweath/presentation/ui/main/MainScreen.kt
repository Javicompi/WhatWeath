package es.jnsoft.whatweath.presentation.ui.main

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.ui.BottomNavigation
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import es.jnsoft.domain.model.Units
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
@ExperimentalCoroutinesApi
fun MainScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val currents = mainViewModel.currentsPresentation.collectAsState(initial = listOf())
    val selectedId = mainViewModel.selectedId.collectAsState(initial = 0L)
    val current = mainViewModel.currentDomain.collectAsState(initial = null)
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val screens = listOf(
        BottomNavScreen.Current,
        BottomNavScreen.Days,
        BottomNavScreen.Search
    )
    val menuActions = listOf(
        MenuAction.Standard,
        MenuAction.Metric,
        MenuAction.Imperial
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            WhatWeathAppBar(
                scope = scope,
                scaffoldState = scaffoldState,
                title = current.value?.name ?: stringResource(id = R.string.app_name),
                menuActions = menuActions,
                onMenuItemClick = { mainViewModel.setUnits(units = it) },
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            WhatWeathAppBottomNavigation(
                navController = navController,
                bottomNavScreens = screens,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        drawerContent = {
            WhatWeathDrawer(
                currents = currents.value,
                selectedId = selectedId.value,
                onItemClick = {
                    scope.launch { scaffoldState.drawerState.close() }
                    mainViewModel.setSelectedId(it)
                    navController.navigate(BottomNavScreen.Current.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { innerPadding ->
        WhatWeathNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                mainViewModel.onStart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}

@Composable
private fun WhatWeathAppBottomNavigation(
    navController: NavHostController,
    bottomNavScreens: List<BottomNavScreen>,
    modifier: Modifier
) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavScreens.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(stringResource(id = screen.resourceId)) },
                alwaysShowLabel = false,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun WhatWeathAppBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    title: String,
    menuActions: List<MenuAction>,
    onMenuItemClick: (Units) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            }
        },
        modifier = modifier,
        actions = {
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                menuActions.onEach { menuAction ->
                    DropdownMenuItem(onClick = {
                        Log.d("MainScreen", "${menuAction.units} clicked")
                        expanded = false
                        onMenuItemClick(menuAction.units)
                    }) {
                        Text(text = stringResource(id = menuAction.label))
                    }
                }
            }
        },
    )
}

@Composable
private fun WhatWeathDrawer(
    currents: List<CurrentPresentation>,
    selectedId: Long,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("MainScreen", "Currents: ${currents.size}")
    if (currents.isNotEmpty()) {
        CurrentsList(
            currents = currents,
            selectedId = selectedId,
            onDrawerItemClick = {
                onItemClick(it)
            },
            modifier = modifier
        )
    } else {
        EmptyList()
    }
}

@Composable
private fun CurrentsList(
    currents: List<CurrentPresentation>,
    selectedId: Long,
    onDrawerItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        items(currents) { current ->
            val isSelected = current.id == selectedId
            DrawerItem(
                current = current,
                isSelected = isSelected,
                onItemClick = { onDrawerItemClick(current.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(12.dp)
            )
        }
    }
}

@Composable
private fun EmptyList(
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.drawer_empty_list),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showSystemUi = true)
@Composable
@ExperimentalCoroutinesApi
fun MainScreenPreview() {
    MainScreen()
}