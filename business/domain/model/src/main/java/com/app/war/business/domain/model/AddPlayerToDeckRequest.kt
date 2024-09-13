package com.app.war.business.domain.model

data class AddPlayerToDeckRequest(
    val deckId: String,
    val playerId: String,
    val cards: List<String>
)
