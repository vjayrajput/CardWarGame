package com.app.war.features.game


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.war.business.domain.main.usecase.AddPlayerToDeckUseCase
import com.app.war.business.domain.main.usecase.CreateNewDeckUseCase
import com.app.war.business.domain.main.usecase.DrawDeckCardsUseCase
import com.app.war.business.domain.main.usecase.DrawPlayersCardsUseCase
import com.app.war.business.domain.model.AddPlayerToDeckRequest
import com.app.war.business.domain.model.DrawPlayerCardRequest
import com.app.war.common.presentation.widgets.mapper.CardToUiStateMapper
import com.app.war.common.presentation.widgets.model.DeckCardState
import com.app.war.common.presentation.widgets.model.DeckPlayerState
import com.app.war.features.game.di.GamePlayViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

@HiltViewModel(assistedFactory = GamePlayViewModelFactory::class)
class GamePlayViewModel @AssistedInject constructor(
    @Assisted val players: Int,
    private val createNewDeckUseCase: CreateNewDeckUseCase,
    private val drawDeckCardsUseCase: DrawDeckCardsUseCase,
    private val addPlayerToDeckUseCase: AddPlayerToDeckUseCase,
    private val drawPlayersCardsUseCase: DrawPlayersCardsUseCase,
    private val cardToUiStateMapper: CardToUiStateMapper,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _deckId = mutableStateOf("")
    val deckId: State<String> = _deckId

    private var _gameHelper = mutableStateOf(CardGameHelper(0))
    val gameHelper: State<CardGameHelper> get() = _gameHelper

    private var _resultMessage = mutableStateOf("")
    val resultMessage: State<String> get() = _resultMessage

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> get() = _errorMessage

    init {
        _gameHelper.value = CardGameHelper(players)
        _resultMessage.value = ""
        setUpGameHelper()
    }

    fun playRound() {
        _resultMessage.value = _gameHelper.value.playRound()
        updatePlayersDrawCards()
    }

    private fun setUpGameHelper() {
        viewModelScope.launch {
            createNewDesk()
        }
    }

    private fun createNewDesk() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                createNewDeckUseCase.invoke().fold(
                    onSuccess = { data ->
                        _deckId.value = data.deckId
                        drawDeckCards()
                    },
                    onFailure = { error ->
                        handleError(error)
                    }
                )
            } catch (e: IOException) {
                handleError(e, "Network error occurred. Please check your internet connection.")
            } catch (e: Exception) {
                handleError(e, "Unexpected error occurred.")
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun drawDeckCards() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                drawDeckCardsUseCase.invoke(deckId.value).fold(
                    onSuccess = { data ->
                        val cards = data.cards.map { c ->
                            cardToUiStateMapper.map(c)
                        }
                        _gameHelper.value.updateDeckCards(cards)
                        setUpPlayers()
                    },
                    onFailure = { error ->
                        handleError(error)
                    }
                )
            } catch (e: IOException) {
                handleError(e, "Network error occurred. Please check your internet connection.")
            } catch (e: Exception) {
                handleError(e, "Unexpected error occurred.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun setUpPlayers() {
        viewModelScope.launch {
            for (player in _gameHelper.value.getPlayers()) {
                addToPileUseCase(player)
            }
        }
    }

    private fun addToPileUseCase(player: DeckPlayerState) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val cards = player.cards.map { it.code }
                val request = AddPlayerToDeckRequest(
                    deckId = deckId.value,
                    playerId = player.id,
                    cards = cards,
                )
                addPlayerToDeckUseCase.invoke(request).fold(
                    onSuccess = { data ->
                        // Handle success
                    },
                    onFailure = { error ->
                        handleError(error)
                    }
                )
            } catch (e: IOException) {
                handleError(e, "Network error occurred. Please check your internet connection.")
            } catch (e: Exception) {
                handleError(e, "Unexpected error occurred.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun updatePlayersDrawCards() {
        viewModelScope.launch {
            try {
                val drawJobs =
                    _gameHelper.value.getRoundPlayersCards().map { (deckPlayer, deckCard) ->
                        async {
                            if (deckCard.priorityValue >= 1) {
                                drawPlayerCards(deckPlayer, deckCard)
                            }
                        }
                    }
                // Wait for all the coroutines to complete
                drawJobs.awaitAll()
                updatePlayerWonCards()
            } catch (e: IOException) {
                handleError(e, "Network error occurred. Please check your internet connection.")
            } catch (e: Exception) {
                handleError(e, "Unexpected error occurred.")
            }
        }
    }

    private suspend fun drawPlayerCards(player: DeckPlayerState, card: DeckCardState) {
        _isLoading.value = true
        try {
            val request = DrawPlayerCardRequest(
                deckId = deckId.value,
                playerId = player.id,
                cards = listOf(card.code)
            )
            drawPlayersCardsUseCase.invoke(request).fold(
                onSuccess = { data ->

                },
                onFailure = { it ->
                    handleError(it, "Failed to draw player cards.")
                }
            )
        } catch (e: IOException) {
            handleError(e, "Network error occurred. Please check your internet connection.")
        } catch (e: Exception) {
            handleError(e, "Unexpected error occurred.")
        }
    }

    private suspend fun updatePlayerWonCards() {
        _isLoading.value = true
        try {
            val cards = _gameHelper.value.getRoundPlayersCards().values.map { it.code }
            val request = AddPlayerToDeckRequest(
                deckId = deckId.value,
                playerId = _gameHelper.value.getGameRoundWinner()?.playerId.orEmpty(),
                cards = cards,
            )
            addPlayerToDeckUseCase.invoke(request).fold(
                onSuccess = { data ->
                },
                onFailure = { it ->
                    handleError(it, "Failed to update player won cards.")
                }
            )
        } catch (e: IOException) {
            handleError(e, "Network error occurred. Please check your internet connection.")
        } catch (e: Exception) {
            handleError(e, "Unexpected error occurred.")
        } finally {
            _isLoading.value = false
        }
    }

    private fun handleError(exception: Throwable, message: String? = null) {
        _isLoading.value = false
        _errorMessage.value = message ?: exception.localizedMessage
        exception.printStackTrace()
        println("Error: ${exception.message}")
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
