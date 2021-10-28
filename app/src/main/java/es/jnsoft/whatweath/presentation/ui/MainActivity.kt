package es.jnsoft.whatweath.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.whatweath.presentation.ui.main.MainScreen
import es.jnsoft.whatweath.presentation.ui.theme.WhatWeathTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalAnimationApi
@AndroidEntryPoint
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WhatWeathTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    MainScreen()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
fun DefaultPreview() {
    WhatWeathTheme {
        ProvideWindowInsets {
            MainScreen()
        }
    }
}