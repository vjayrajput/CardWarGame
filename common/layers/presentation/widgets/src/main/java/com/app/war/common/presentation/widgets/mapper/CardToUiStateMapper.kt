package com.app.war.common.presentation.widgets.mapper

import com.app.war.business.domain.model.Card
import com.app.war.common.presentation.widgets.model.DeckCardState
import javax.inject.Inject

class CardToUiStateMapper @Inject constructor() {

    fun map(param: Card): DeckCardState {
        return DeckCardState(
            code = param.code,
            image = param.image,
            value = param.value,
            suit = param.suit,
            priorityValue = param.priorityValue,
            suitRank = param.suitRank,
        )
    }
}
