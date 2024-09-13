package com.app.war.business.data.entity

import com.google.gson.annotations.SerializedName

data class CardsEntity(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("deck_id")
    val deckId: String,
    @SerializedName("remaining")
    val remaining: Int,
    @SerializedName("cards")
    val cards: List<CardEntity>,
)
