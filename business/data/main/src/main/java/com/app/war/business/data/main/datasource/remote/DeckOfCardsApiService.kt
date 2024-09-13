package com.app.war.business.data.main.datasource.remote

import com.app.war.business.data.entity.CardsEntity
import com.app.war.business.data.entity.DeckEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckOfCardsApiService {

    @GET("deck/new/shuffle/")
    suspend fun createNewDeckWithShuffle(): DeckEntity

    @GET("deck/{deck_id}/draw/")
    suspend fun drawDeckCards(
        @Path("deck_id") deckId: String,
        @Query("count") count: Int = 52,
    ): CardsEntity

    @GET("deck/{deck_id}/pile/{pile_name}/add/")
    suspend fun addPlayerToDeck(
        @Path("deck_id") deckId: String,
        @Path("pile_name") playerId: String,
        @Query("cards") cards: String,
    ): DeckEntity

    @GET("deck/{deck_id}/pile/{pile_name}/draw/")
    suspend fun drawPlayersCards(
        @Path("deck_id") deckId: String,
        @Path("pile_name") playerId: String,
        @Query("cards") cards: String,
    ): CardsEntity
}
