package com.app.war.features.game.di

import com.app.war.features.game.GamePlayViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
interface GamePlayViewModelFactory {
    fun create(players: Int): GamePlayViewModel
}
