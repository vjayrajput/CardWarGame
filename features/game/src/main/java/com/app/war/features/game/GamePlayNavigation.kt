package com.app.war.features.game

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.war.common.navigation.Screen

fun NavController.navigateToGamePlayScreen(players: Int) {
    this.navigate(Screen.GamePlay.route.plus("/$players"))
}

fun NavGraphBuilder.gamePlayScreen(
    onNavigateBack: () -> Unit,
) {
    composable(
        Screen.GamePlay.route.plus(Screen.GamePlay.objectPath),
        arguments = listOf(navArgument(Screen.GamePlay.objectName) {
            type = NavType.IntType
        })
    ) { backStackEntry ->
        val players = backStackEntry.arguments?.getInt(Screen.GamePlay.objectName) ?: 2
        GamePlayScreen(
            players = players,
            onNavigateBack = onNavigateBack
        )
    }
}
