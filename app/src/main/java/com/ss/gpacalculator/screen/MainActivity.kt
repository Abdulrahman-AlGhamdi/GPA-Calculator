package com.ss.gpacalculator.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ss.gpacalculator.ui.navigation.Navigation
import com.ss.gpacalculator.ui.theme.GpaCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(content = { GpaCalculatorApp() })
    }

    @Composable
    @OptIn(ExperimentalAnimationApi::class)
    private fun GpaCalculatorApp(): Unit = GpaCalculatorTheme {
        navController = rememberAnimatedNavController()

        Navigation(navController = navController)
    }
}