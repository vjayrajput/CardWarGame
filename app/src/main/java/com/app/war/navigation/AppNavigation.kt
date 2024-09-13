package com.app.war.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.war.common.navigation.Screen
import com.app.war.features.game.gamePlayScreen
import com.app.war.features.game.navigateToGamePlayScreen
import com.app.war.features.home.gameHomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = Screen.GameHome.route) {
        gameHomeScreen(
            onNavigateToStartGame = navController::navigateToGamePlayScreen
        )
        gamePlayScreen(onNavigateBack = {
            navController.navigateUp()
        })
    }
}
