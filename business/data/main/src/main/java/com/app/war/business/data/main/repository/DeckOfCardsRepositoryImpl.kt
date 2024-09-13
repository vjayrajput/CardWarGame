package com.app.war.business.data.main.repository

import com.app.war.business.data.main.datasource.DeckOfCardsRemoteDataSource
import com.app.war.business.data.main.mapper.CardsMapper
import com.app.war.business.data.main.mapper.DeckMapper
import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import com.app.war.business.domain.model.AddPlayerToDeckRequest
import com.app.war.business.domain.model.Cards
import com.app.war.business.domain.model.Deck
import com.app.war.business.domain.model.DrawPlayerCardRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeckOfCardsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DeckOfCardsRemoteDataSource,
    private val deckMapper: DeckMapper,
    private val cardsMapper: CardsMapper,
) : DeckOfCardsRepository {

    override suspend fun createNewDeckWithShuffle(): Result<Deck> =
        withContext(Dispatchers.IO) {
            remoteDataSource.createNewDeckWithShuffle()
                .map { deckMapper.mapTo(it) }
        }

    override suspend fun drawDeckCards(deckId: String): Result<Cards> =
        withContext(Dispatchers.IO) {
            remoteDataSource.drawDeckCards(deckId = deckId)
                .map { cardsMapper.mapTo(it) }
        }

    override suspend fun addPlayerToDeck(request: AddPlayerToDeckRequest): Result<Deck> =
        withContext(Dispatchers.IO) {
            remoteDataSource.addPlayerToDeck(request = request)
                .map { deckMapper.mapTo(it) }
        }

    override suspend fun drawPlayersCards(request: DrawPlayerCardRequest): Result<Cards> =
        withContext(Dispatchers.IO) {
            remoteDataSource.drawPlayersCards(request = request)
                .map { cardsMapper.mapTo(it) }
        }
}
