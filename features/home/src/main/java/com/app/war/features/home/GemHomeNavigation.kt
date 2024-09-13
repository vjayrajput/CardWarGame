package com.app.war.features.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.war.common.navigation.Screen

fun NavGraphBuilder.gameHomeScreen(
    onNavigateToStartGame: (Int) -> Unit,
) {
    composable(Screen.GameHome.route) {
        GameHomeScreen(
            onNavigateToStartGame = onNavigateToStartGame,
        )
    }
}
