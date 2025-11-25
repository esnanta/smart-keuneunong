package com.smart.keuneunong.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smart.keuneunong.ui.splash.SplashScreen
import com.smart.keuneunong.ui.splash.SplashViewModel
import com.smart.keuneunong.ui.home.DashboardScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { backStackEntry ->
            // obtain a hilt viewmodel scoped to the navBackStackEntry
            val viewModel: SplashViewModel = hiltViewModel(backStackEntry)
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                viewModel = viewModel
            )
        }

        composable(Screen.Dashboard.route) {
            // Temporary placeholder
            DashboardScreen()
        }
    }
}
