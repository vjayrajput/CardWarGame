package com.app.war.business.domain.di

import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import com.app.war.business.domain.main.usecase.AddPlayerToDeckUseCase
import com.app.war.business.domain.main.usecase.CreateNewDeckUseCase
import com.app.war.business.domain.main.usecase.DrawDeckCardsUseCase
import com.app.war.business.domain.main.usecase.DrawPlayersCardsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DeckOfCardsDomainModule {

    @Provides
    fun provideCreateNewDeckUseCase(repository: DeckOfCardsRepository) =
        CreateNewDeckUseCase(repository)

    fun provideAddPlayerToDeckUseCase(repository: DeckOfCardsRepository) =
        AddPlayerToDeckUseCase(repository)

    @Provides
    fun provideDrawDeckCardsUseCase(repository: DeckOfCardsRepository) =
        DrawDeckCardsUseCase(repository)

    @Provides
    fun provideDrawPlayersCardsUseCase(repository: DeckOfCardsRepository) =
        DrawPlayersCardsUseCase(repository)
}
