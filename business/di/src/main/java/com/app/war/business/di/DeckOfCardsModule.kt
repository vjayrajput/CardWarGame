package com.app.war.business.di

import com.app.war.business.domain.di.DeckOfCardsDomainModule
import com.app.war.business.data.di.DeckOfCardsDataModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DeckOfCardsDomainModule::class, DeckOfCardsDataModule::class])
@InstallIn(SingletonComponent::class)
object DeckOfCardsModule {
    //DO NOTHING
}
