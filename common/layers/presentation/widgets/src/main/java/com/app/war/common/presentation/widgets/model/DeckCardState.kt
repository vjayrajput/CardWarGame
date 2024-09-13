package com.app.war.common.presentation.widgets.model

import com.app.war.common.general.models.State

data class DeckCardState(
    override val id: String = "",
    val code: String = "0",
    val image: String = "https://deckofcardsapi.com/static/img/back.png",
    val value: String = "0",
    val suit: String = "",
    val priorityValue: Int = 0,
    val suitRank: Int = 0,
) : State {

    companion object {
        val EMPTY = DeckCardState()
    }
}
