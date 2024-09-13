package com.app.war.business.data.entity

import com.google.gson.annotations.SerializedName

data class CardEntity(
    @SerializedName("code")
    val code: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("value")
    val value: String,
    @SerializedName("suit")
    val suit: String,
)
