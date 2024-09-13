package com.app.war.business.domain.model

data class Cards(
    val success: Boolean,
    val cards: List<Card>,
    val deckId: String,
    val remaining: Int
)
