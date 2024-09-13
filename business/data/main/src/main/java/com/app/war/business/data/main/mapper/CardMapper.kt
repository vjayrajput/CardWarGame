package com.app.war.business.data.main.mapper

import com.app.war.business.data.entity.CardEntity
import com.app.war.business.domain.model.Card
import com.app.war.common.domain.api.Mapper
import javax.inject.Inject

class CardMapper @Inject constructor() : Mapper<CardEntity, Card> {

    private val valueToCode = mapOf(
        "JACK" to 11,
        "QUEEN" to 12,
        "KING" to 13,
        "ACE" to 14
    )

    private fun getSuitRank(suit: String): Int {
        return when (suit) {
            "SPADES" -> 4
            "HEARTS" -> 3
            "DIAMONDS" -> 2
            "CLUBS" -> 1
            else -> 0 // Default if suit is not recognized
        }
    }

    override fun mapTo(type: CardEntity?): Card {
        return Card(
            code = type?.code.orEmpty(),
            image = type?.image.orEmpty(),
            value = type?.value.orEmpty(),
            suit = type?.suit.orEmpty(),
            priorityValue = valueToCode[type?.value] ?: type?.value?.toInt() ?: 0,
            suitRank = getSuitRank(suit = type?.suit.orEmpty())
        )
    }
}
