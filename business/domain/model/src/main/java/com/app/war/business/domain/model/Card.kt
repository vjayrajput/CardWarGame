package com.app.war.business.domain.model

data class Card(
    val code: String,
    val image: String,
    val value: String,
    val suit: String,
    val priorityValue: Int,
    val suitRank: Int,
)
