package com.app.war.business.data.main.mapper

import com.app.war.business.data.entity.DeckEntity
import com.app.war.business.domain.model.Deck
import com.app.war.common.domain.api.Mapper
import com.app.war.common.general.extensions.orFalse
import javax.inject.Inject

class DeckMapper @Inject constructor() :
    Mapper<DeckEntity, Deck> {

    override fun mapTo(type: DeckEntity?): Deck {
        return Deck(
            success = type?.success.orFalse(),
            deckId = type?.deckId.orEmpty(),
            shuffled = type?.shuffled.orFalse(),
            remaining = type?.remaining ?: 0,
        )
    }
}
