package com.app.war.features.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameHomeViewModel @Inject constructor() : ViewModel() {

    private val _players = mutableListOf(2, 3, 4)
    val players: List<Int>
        get() = _players

    private var _numPlayers = mutableIntStateOf(2)
    val numPlayers: State<Int> get() = _numPlayers

    fun updatePlayerCount(count: Int) {
        _numPlayers.value = count
    }
}
