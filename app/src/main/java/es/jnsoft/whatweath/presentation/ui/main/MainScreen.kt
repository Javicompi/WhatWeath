package es.jnsoft.whatweath.presentation.ui.main

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import es.jnsoft.domain.enums.Units
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.BasePresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
@ExperimentalCoroutinesApi
fun MainScreen() {
    val mainViewModel: MainViewModel = hiltViewModel()
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val screens = listOf(
        Screen.Current,
        Screen.Hours,
        Screen.Days,
        Screen.Search
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
                menuActions = menuActions,
                onMenuItemClick = { mainViewModel.setUnits(units = it) },
                modifier = Modifier.statusBarsPadding()
            )
        },
        bottomBar = {
            WhatWeathAppBottomNavigation(
                navController = navController,
                screens = screens,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        drawerContent = {
            WhatWeathDrawer(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController,
                currentFlow = mainViewModel.presentation,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { innerPadding ->
        WhatWeathNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun WhatWeathAppBottomNavigation(
    navController: NavHostController,
    screens: List<Screen>,
    modifier: Modifier
) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                label = { Text(stringResource(id = screen.resourceId)) },
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun WhatWeathAppBar(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    menuActions: List<MenuAction>,
    onMenuItemClick: (Units) -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
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
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    modifier: Modifier,
    currentFlow: Flow<List<CurrentPresentation>?>
) {
    val currents = currentFlow.collectAsState(initial = emptyList()).value
    Log.d("MainScreen", "Currents: ${currents?.size}")
    if (currents != null && currents.isNotEmpty()) {
        CurrentsList(
            currents = currents,
            modifier = modifier,
            onDrawerItemClick = {
                // TODO to be implemented
            }
        )
    } else {
        EmptyList()
    }
}

@Composable
private fun CurrentsList(
    currents: List<CurrentPresentation>,
    modifier: Modifier = Modifier,
    onDrawerItemClick: (BasePresentation) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier
    ) {
        items(currents) { current ->
            DrawerItem(item = current, selected = false, onItemClick = onDrawerItemClick)
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

@Composable
private fun DrawerItem(
    item: CurrentPresentation,
    selected: Boolean,
    onItemClick: (CurrentPresentation) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .padding(start = 10.dp)
    ) {
        Text(text = item.name, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = item.description, fontSize = 18.sp)
    }
}

@Preview(showSystemUi = true)
@Composable
@ExperimentalCoroutinesApi
fun MainScreenPreview() {
    MainScreen()
}