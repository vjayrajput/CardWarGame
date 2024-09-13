package com.app.war.common.navigation

sealed class Screen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = ""
) {
    object GameHome : Screen(
        route = "game_home_screen"
    )

    object GamePlay :
        Screen(
            route = "game_play_screen",
            objectName = "players",
            objectPath = "/{players}"
        )
}
