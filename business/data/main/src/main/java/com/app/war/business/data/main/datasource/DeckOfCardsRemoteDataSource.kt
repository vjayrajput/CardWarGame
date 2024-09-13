package com.app.war.business.data.main.datasource

import com.app.war.business.data.entity.CardsEntity
import com.app.war.business.data.entity.DeckEntity
import com.app.war.business.domain.model.AddPlayerToDeckRequest
import com.app.war.business.domain.model.DrawPlayerCardRequest

interface DeckOfCardsRemoteDataSource {

    suspend fun createNewDeckWithShuffle(): Result<DeckEntity>

    suspend fun drawDeckCards(deckId: String): Result<CardsEntity>

    suspend fun addPlayerToDeck(request: AddPlayerToDeckRequest): Result<DeckEntity>

    suspend fun drawPlayersCards(request: DrawPlayerCardRequest): Result<CardsEntity>
}
