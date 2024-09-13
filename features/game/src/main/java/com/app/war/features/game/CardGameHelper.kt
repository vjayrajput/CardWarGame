package com.app.war.features.game

import com.app.war.common.presentation.widgets.model.DeckCardState
import com.app.war.common.presentation.widgets.model.DeckPlayerState

class CardGameHelper(private val numPlayers: Int) {
    private val deck: MutableList<DeckCardState> = mutableListOf()
    private val _players: MutableList<DeckPlayerState> = mutableListOf()
    private var _roundPlayersCards: Map<DeckPlayerState, DeckCardState> = mutableMapOf()
    private var _gameRoundWinner: DeckPlayerState? = null
    private var _gameFinished = false

    init {
        initPlayers()
    }

    fun updateDeckCards(cards: List<DeckCardState>) {
        deck.clear()
        deck.addAll(cards)

        distributeCards()
    }

    private fun initPlayers() {
        repeat(numPlayers) {
            _players.add(
                DeckPlayerState(
                    id = "player${it + 1}",
                    playerId = "player${it + 1}",
                    name = "Player ${it + 1}",
                    cards = mutableListOf()
                )
            )
        }
    }

    private fun distributeCards() {
        if (_players.isEmpty()) return

        var currentPlayer = 0
        for (card in deck) {
            _players[currentPlayer].cards.add(card)
            currentPlayer = (currentPlayer + 1) % numPlayers
        }
    }

    private fun playersDrawCards(): Map<DeckPlayerState, DeckCardState> {
        val drawnCards = _players.associateWith { player ->
            val card = if (player.cards.isEmpty()) {
                DeckCardState.EMPTY
            } else {
                val randomIndex = (0 until player.cards.size).random()
                player.cards.removeAt(randomIndex)
            }
            card
        }
        _roundPlayersCards = drawnCards
        return drawnCards
    }

    fun playRound(): String {
        val drawnCards = playersDrawCards()

        val winner = determineRoundWinner(drawnCards)

        // Give all cards to the winner
        winner.cards.addAll(drawnCards.values)

        // Update the battles won counter
        winner.battlesWon++

        // Check if any player has won the game
        val gameWinner = _players.find { it.battlesWon >= 10 }
        return if (gameWinner != null) {
            _gameFinished = true
            _gameRoundWinner = gameWinner
            "Game Over! \n${gameWinner.name} wins the game!"
        } else {
            _gameRoundWinner = winner
            "P${winner.name.last()} wins the round!"
        }
    }

    private fun determineRoundWinner(drawnCards: Map<DeckPlayerState, DeckCardState>): DeckPlayerState {
        // Find the maximum card value
        val highestCardValue = drawnCards.values.maxOf { it.priorityValue }

        // Filter players with the highest card value
        val playersWithHighestCard =
            drawnCards.filterValues { it.priorityValue == highestCardValue }.keys

        // If there is more than one player with the highest card value
        return if (playersWithHighestCard.size > 1) {
            // Find the player with the highest suit rank
            val highestSuitRank = drawnCards.filterKeys { it in playersWithHighestCard }
                .maxByOrNull { it.value.suitRank }?.key

            // If there is a tie in suit rank, decide by the number of cards in the deck
            if (playersWithHighestCard.count { it == highestSuitRank } > 1) {
                val mostCardsPlayer = playersWithHighestCard.maxByOrNull { it.cards.size }
                mostCardsPlayer ?: playersWithHighestCard.random()
            } else {
                highestSuitRank!!
            }
        } else {
            playersWithHighestCard.first()
        }
    }

    fun getPlayers(): List<DeckPlayerState> = _players

    fun getRoundPlayersCards(): Map<DeckPlayerState, DeckCardState> = _roundPlayersCards

    fun getGameRoundWinner(): DeckPlayerState? = _gameRoundWinner

    fun isGameFinished() = _gameFinished
}
