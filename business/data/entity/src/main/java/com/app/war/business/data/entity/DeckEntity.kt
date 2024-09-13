package com.app.war.business.data.entity

import com.google.gson.annotations.SerializedName

data class DeckEntity(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("deck_id")
    val deckId: String,
    @SerializedName("shuffled")
    val shuffled: Boolean,
    @SerializedName("remaining")
    val remaining: Int,
)
