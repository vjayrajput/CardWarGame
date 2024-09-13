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
import androidx.compose.foundation.shape.CircleShape
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
fun PlayerWidget(player: DeckPlayerState) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = player.name, style = MaterialTheme.typography.titleMedium)
        // Display the card back and card count
        CardView(cardCount = player.cards.size, battlesWon = player.battlesWon)
    }
}


@Composable
private fun CardView(cardCount: Int, battlesWon: Int = 0) {
    Box(
        modifier = Modifier
            .size(80.dp, 120.dp)
            .background(Color.White, shape = RoundedCornerShape(4.dp))
    ) {
        // Load and display the back of a card image
        Image(
            painter = rememberAsyncImagePainter("https://deckofcardsapi.com/static/img/back.png"),
            contentDescription = "Card Back",
            modifier = Modifier.fillMaxSize()
        )

        // Display the card count
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 8.dp)
                .size(32.dp) // Adjust size as needed
                .background(Color.LightGray, shape = CircleShape), // Circle background
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$cardCount",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }
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
                .align(Alignment.TopCenter),
        ) {
            // Badge Icon
            Icon(
                imageVector = Icons.Filled.MilitaryTech, // Replace with your desired icon
                contentDescription = "Won Game Badge",
                tint = Color.Yellow,
                modifier = Modifier.size(24.dp)
            )

            // Count Text
            Text(
                text = "$battlesWon",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }
    }
}
