package com.app.war.business.data.main.datasource.remote

import com.app.war.business.data.entity.CardsEntity
import com.app.war.business.data.entity.DeckEntity
import com.app.war.business.data.main.datasource.DeckOfCardsRemoteDataSource
import com.app.war.business.domain.model.AddPlayerToDeckRequest
import com.app.war.business.domain.model.DrawPlayerCardRequest
import com.app.war.common.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeckOfCardsRemoteDataSourceImpl @Inject constructor(
    private val apiService: DeckOfCardsApiService,
) : DeckOfCardsRemoteDataSource {

    override suspend fun createNewDeckWithShuffle(): Result<DeckEntity> =
        withContext(Dispatchers.IO) {
            safeApiCall { apiService.createNewDeckWithShuffle() }
        }

    override suspend fun drawDeckCards(deckId: String): Result<CardsEntity> =
        withContext(Dispatchers.IO) {
            safeApiCall { apiService.drawDeckCards(deckId = deckId) }
        }

    override suspend fun addPlayerToDeck(request: AddPlayerToDeckRequest): Result<DeckEntity> =
        withContext(Dispatchers.IO) {
            safeApiCall {
                apiService.addPlayerToDeck(
                    deckId = request.deckId,
                    playerId = request.playerId,
                    cards = request.cards.joinToString(",")
                )
            }
        }

    override suspend fun drawPlayersCards(request: DrawPlayerCardRequest): Result<CardsEntity> =
        withContext(Dispatchers.IO) {
            safeApiCall {
                apiService.drawPlayersCards(
                    deckId = request.deckId,
                    playerId = request.playerId,
                    cards = request.cards.joinToString(",")
                )
            }
        }
}
