package com.app.war.business.domain.main.usecase

import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import com.app.war.business.domain.model.Cards
import com.app.war.business.domain.model.DrawPlayerCardRequest
import com.app.war.common.domain.api.UseCase
import javax.inject.Inject

class DrawPlayersCardsUseCase @Inject constructor(
    private val repository: DeckOfCardsRepository
) : UseCase.SuspendingParameterized<DrawPlayerCardRequest, Result<Cards>> {

    override suspend fun invoke(param: DrawPlayerCardRequest): Result<Cards> {
        return repository.drawPlayersCards(request = param)
    }
}
