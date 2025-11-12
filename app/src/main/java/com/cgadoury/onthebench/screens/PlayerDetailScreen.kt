package com.cgadoury.onthebench.screens

import android.inputmethodservice.Keyboard
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.ui.components.StatItem
import com.cgadoury.onthebench.ui.components.StatusStatCard

/**
 *
 */
@Composable
fun PlayerDetailScreen(
    modifier: Modifier,
    player: Player
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(85.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
            model = ImageRequest.Builder(
                LocalContext.current
            ).data(player.headshot)
                .build(),
            contentDescription = null,
            imageLoader = ImageLoader.Builder(
                LocalContext.current
            ).components {
                add(SvgDecoder.Factory())
            }.build()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "${player.firstName?.default} ${player.lastName?.default}",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("GP", player.featuredStats?.regularSeason?.subSeason?.gamesPlayed)
            StatItem("G", player.featuredStats?.regularSeason?.subSeason?.goals)
            StatItem("A", player.featuredStats?.regularSeason?.subSeason?.assists)
            StatItem("+/-", player.featuredStats?.regularSeason?.subSeason?.plusMinus)
            StatItem("PIM", player.featuredStats?.regularSeason?.subSeason?.pim)
        }

        PlayerStatCard(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            player = player
        )
    }
}

@Composable
fun PlayerStatCard(
    modifier: Modifier,
    player: Player
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val points = player.featuredStats.regularSeason.subSeason.points
            val gamesPlayed = player.featuredStats.regularSeason.subSeason.gamesPlayed
            val shootingPctg = (
                    player.featuredStats.regularSeason.subSeason.shootingPctg * 100
                    ).toInt()
            val pointsPerGame: Double = if (gamesPlayed > 0) {
                points.toDouble() / gamesPlayed
            } else {
                0.0
            }
            val shotsPerGame: Double = if (gamesPlayed > 0) {
                player.featuredStats.regularSeason.subSeason.shots.toDouble() / gamesPlayed
            } else {
                0.0
            }

            StatusStatCard(
                modifier = Modifier.weight(1f),
                label = "P/GP",
                value = String.format("%.2f", pointsPerGame),
                isGood = pointsPerGame > 0.75
            )
            StatusStatCard(
                modifier = Modifier.weight(1f),
                label = "S/GP",
                value = String.format("%.2f", shotsPerGame),
                isGood = shotsPerGame > 1.5
            )
            StatusStatCard(
                modifier = Modifier.weight(1f),
                label = "Shooting Pctg.",
                value = "${shootingPctg}%",
                isGood = shootingPctg > 15
            )
        }
    }
}