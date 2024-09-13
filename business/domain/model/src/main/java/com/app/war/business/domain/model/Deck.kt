package com.app.war.business.domain.model

data class Deck(
    val success: Boolean,
    val deckId: String,
    val shuffled: Boolean,
    val remaining: Int
)
