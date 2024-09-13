package com.app.war.common.ui.compositions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.war.common.presentation.widgets.model.DeckPlayerState

@Composable
fun RoundWidget(player: DeckPlayerState, cardImage: String, isWinner: Boolean = false) {
    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "P${player.name.last()}",
            style = MaterialTheme.typography.titleSmall
        )
        RoundCardsView(cardImage = cardImage, isWinner)
    }
}

@Composable
private fun RoundCardsView(cardImage: String, isWinner: Boolean = false) {
    Box(
        modifier = Modifier
            .size(70.dp, 105.dp)
            .background(Color.White, shape = RoundedCornerShape(4.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(cardImage),
            contentDescription = "Card Back",
            modifier = Modifier.fillMaxSize()
        )

        if (isWinner) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        Color(0xFFFF5722),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(4.dp)
                    .align(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector = Icons.Filled.MilitaryTech,
                    contentDescription = "Won Game Badge",
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}