package com.ss.gpacalculator.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ss.gpacalculator.screen.home.HomeScreen
import com.ss.gpacalculator.screen.overall.OverallScreen
import com.ss.gpacalculator.screen.overall.OverallViewModel
import com.ss.gpacalculator.screen.semester.SemesterScreen
import com.ss.gpacalculator.screen.semester.SemesterViewModel
import com.ss.gpacalculator.screen.settings.SettingsScreen
import com.ss.gpacalculator.screen.settings.SettingsViewModel

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun Navigation(navController: NavHostController) = AnimatedNavHost(
    navController = navController,
    startDestination = Screen.HomeScreen.route,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None }
) {
    composable(route = Screen.HomeScreen.route, content = {
        HomeScreen(
            onOverallClick = { navController.navigate(Screen.OverallScreen.route) },
            onSemesterClick = { navController.navigate(Screen.SemesterScreen.route) },
            onSettingsClick = { navController.navigate(Screen.SettingsScreen.route) }
        )
    })
    composable(route = Screen.OverallScreen.route, content = {
        val viewModel = hiltViewModel<OverallViewModel>()
        val subjectsState by viewModel.overallState.collectAsState()

        OverallScreen(
            subjectsState = subjectsState,
            onAddClick = viewModel::addSubject,
            onCalculateClick = viewModel::calculateOverallGpa,
            onBackClick = navController::popBackStack
        )
    })
    composable(route = Screen.SemesterScreen.route, content = {
        val viewModel = hiltViewModel<SemesterViewModel>()
        val subjectsState by viewModel.semesterState.collectAsState()

        SemesterScreen(
            subjectsState = subjectsState,
            onAddClick = viewModel::addSubject,
            onCalculateClick = viewModel::calculateSemesterGpa,
            onBackClick = navController::popBackStack
        )
    })
    composable(route = Screen.SettingsScreen.route, content = {
        val viewModel = hiltViewModel<SettingsViewModel>()

        SettingsScreen(
            onContactClick = viewModel::openTwitter,
            onBackClick = navController::popBackStack
        )
    })
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "home")
    object OverallScreen : Screen(route = "overall")
    object SemesterScreen : Screen(route = "semester")
    object SettingsScreen : Screen(route = "settings")

    fun withParams(vararg args: String): String = buildString {
        append(route)
        args.forEach { append("/{$it}") }
    }

    fun <T> withArgs(vararg params: T): String = buildString {
        append(route)
        params.forEach { append("/$it") }
    }
}