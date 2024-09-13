package com.app.war.business.domain.main.repository

import com.app.war.business.domain.model.AddPlayerToDeckRequest
import com.app.war.business.domain.model.Cards
import com.app.war.business.domain.model.Deck
import com.app.war.business.domain.model.DrawPlayerCardRequest

interface DeckOfCardsRepository {

    suspend fun createNewDeckWithShuffle(): Result<Deck>

    suspend fun drawDeckCards(deckId: String): Result<Cards>

    suspend fun addPlayerToDeck(request: AddPlayerToDeckRequest): Result<Deck>

    suspend fun drawPlayersCards(request: DrawPlayerCardRequest): Result<Cards>
}
