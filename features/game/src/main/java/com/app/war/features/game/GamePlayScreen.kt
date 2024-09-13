package com.app.war.features.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.war.common.presentation.widgets.model.DeckPlayerState
import com.app.war.common.ui.compositions.ExitAlertDialog
import com.app.war.common.ui.compositions.FullScreenLoader
import com.app.war.common.ui.compositions.GameWinnerDialog
import com.app.war.common.ui.compositions.PlayerWidget
import com.app.war.common.ui.compositions.RoundWidget
import com.app.war.common.ui.resources.strings.StringResources
import com.app.war.features.game.di.GamePlayViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePlayScreen(
    players: Int,
    onNavigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<GamePlayViewModel, GamePlayViewModelFactory> { factory ->
        factory.create(players = players)
    }

    val openDialog = remember { mutableStateOf(false) }
    val isLoading = viewModel.isLoading.collectAsState()
    val resultMessage by viewModel.resultMessage
    val gameHelper by viewModel.gameHelper
    val errorMessage by viewModel.errorMessage

    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect to show the Snackbar whenever errorMessage changes
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
            viewModel.clearError() // Clear the error after displaying the Snackbar
        }
    }

    BackHandler {
        openDialog.value = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(StringResources.titleGamePlay))
                },
                navigationIcon = {
                    IconButton(onClick = { openDialog.value = true }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF4CAF50)), // Green table background
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            TopRowPlayerViews(gameHelper = gameHelper)

            CenterRoundViews(
                gameHelper = gameHelper,
                resultMessage = resultMessage
            ) {
                viewModel.playRound()
            }

            BottomRowPlayerViews(gameHelper = gameHelper)

        }

        if (isLoading.value) {
            FullScreenLoader()
        }

        if (openDialog.value) {
            ExitAlertDialog(
                cancelButtonClick = {
                    openDialog.value = it
                }, okButtonClick = {
                    openDialog.value = false
                    onNavigateBack()
                })
        }

        if (gameHelper.isGameFinished()) {
            GameWinnerDialog(playerName = gameHelper.getGameRoundWinner()?.name.orEmpty()) {
                onNavigateBack()
            }
        }
    }
}

@Composable
private fun TopRowPlayerViews(gameHelper: CardGameHelper) {
    RenderPlayerView(players = gameHelper.getPlayers().take(2))
}

@Composable
private fun BottomRowPlayerViews(gameHelper: CardGameHelper) {
    RenderPlayerView(players = gameHelper.getPlayers().drop(2))
}

@Composable
private fun RenderPlayerView(players: List<DeckPlayerState>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        players.forEach { player ->
            PlayerWidget(player)
        }
    }
}

@Composable
private fun CenterRoundViews(
    gameHelper: CardGameHelper,
    resultMessage: String,
    playRound: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!gameHelper.isGameFinished()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                gameHelper.getRoundPlayersCards().forEach { (player, card) ->
                    RoundWidget(
                        player = player,
                        cardImage = card.image,
                        isWinner = player.name == gameHelper.getGameRoundWinner()?.name,
                    )
                }
            }

            Button(onClick = playRound) {
                Text(
                    text = if (resultMessage.isNotEmpty()) stringResource(StringResources.nextRound) else stringResource(
                        StringResources.playNow
                    )
                )
            }
        }

        if (resultMessage.isNotEmpty()) {
            Text(
                modifier = Modifier,
                text = resultMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
