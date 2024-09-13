package com.app.war.business.domain.main.usecase

import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import com.app.war.business.domain.model.AddPlayerToDeckRequest
import com.app.war.business.domain.model.Deck
import com.app.war.common.domain.api.UseCase
import javax.inject.Inject

class AddPlayerToDeckUseCase @Inject constructor(
    private val repository: DeckOfCardsRepository
) : UseCase.SuspendingParameterized<AddPlayerToDeckRequest, Result<Deck>> {

    override suspend fun invoke(param: AddPlayerToDeckRequest): Result<Deck> {
        return repository.addPlayerToDeck(request = param)
    }
}
