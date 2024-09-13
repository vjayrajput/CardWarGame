package com.app.war.business.domain.main.usecase

import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import com.app.war.business.domain.model.Cards
import com.app.war.common.domain.api.UseCase
import javax.inject.Inject

class DrawDeckCardsUseCase @Inject constructor(
    private val repository: DeckOfCardsRepository
) : UseCase.SuspendingParameterized<String, Result<Cards>> {

    override suspend fun invoke(param: String): Result<Cards> {
        return repository.drawDeckCards(deckId = param)
    }
}
