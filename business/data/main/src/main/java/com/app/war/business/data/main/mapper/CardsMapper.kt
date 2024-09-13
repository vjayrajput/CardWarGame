package com.app.war.business.data.main.mapper

import com.app.war.business.data.entity.CardsEntity
import com.app.war.business.domain.model.Cards
import com.app.war.common.domain.api.Mapper
import com.app.war.common.general.extensions.orFalse
import javax.inject.Inject

class CardsMapper @Inject constructor(
    private val cardMapper: CardMapper,
) : Mapper<CardsEntity, Cards> {

    override fun mapTo(type: CardsEntity?): Cards {
        return Cards(
            success = type?.success.orFalse(),
            deckId = type?.deckId.orEmpty(),
            remaining = type?.remaining ?: 0,
            cards = type?.cards?.map {
                cardMapper.mapTo(it)
            } ?: emptyList(),
        )
    }
}
