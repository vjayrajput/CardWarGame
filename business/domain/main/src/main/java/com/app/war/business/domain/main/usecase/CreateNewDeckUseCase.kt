package com.app.war.business.domain.main.usecase

import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import com.app.war.business.domain.model.Deck
import com.app.war.common.domain.api.UseCase
import javax.inject.Inject

class CreateNewDeckUseCase @Inject constructor(
    private val repository: DeckOfCardsRepository
) : UseCase.Suspending<Result<Deck>> {

    override suspend fun invoke(): Result<Deck> {
        return repository.createNewDeckWithShuffle()
    }
}
