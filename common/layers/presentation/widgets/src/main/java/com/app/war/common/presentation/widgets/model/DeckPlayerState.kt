package com.app.war.common.presentation.widgets.model

import com.app.war.common.general.models.State

data class DeckPlayerState(
    override val id: String = "",
    val playerId: String = "",
    val name: String = "",
    val cards: MutableList<DeckCardState> = mutableListOf(),
    var battlesWon: Int = 0,
) : State {

    companion object {
        val EMPTY = DeckPlayerState()
    }
}
