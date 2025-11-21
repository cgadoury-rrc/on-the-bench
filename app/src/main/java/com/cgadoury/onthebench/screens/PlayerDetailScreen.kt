package com.cgadoury.onthebench.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.ui.components.StatItem
import com.cgadoury.onthebench.ui.components.StatusStatCard
import com.cgadoury.onthebench.ui.theme.TeamColors

/**
 * Purpose - player detail screen - show details for a specific player
 * @param modifier: The modifier to use
 * @param player: The player to handle
 * @return Unit
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
        val teamAbbrev = player.currentTeamAbbrev
        val teamColor = TeamColors.colors[teamAbbrev] ?: Color.White
        val backgroundColors = listOf(
            teamColor,
            teamColor.copy(alpha = 0.8f),
            teamColor.copy(alpha = 0.5f),
            teamColor.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.surface
        )
        Box(
            modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = backgroundColors
                    )
                )
        ) {
            Column(
                modifier = modifier
                    .padding(
                        vertical = 26.dp,
                        horizontal = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(110.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .background(teamColor),
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
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "${player.firstName?.default} ${player.lastName?.default}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical=8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        label = "GP",
                        value = if (player.seasonTotals.isNotEmpty())
                            player.seasonTotals[player.seasonTotals.size-1].gamesPlayed else 0
                    )
                    StatItem(
                        label = "G",
                        value = player.featuredStats.regularSeason.subSeason.goals
                    )
                    StatItem(
                        label = "A",
                        value = player.featuredStats.regularSeason.subSeason.assists
                    )
                    StatItem(
                        label = "+/-",
                        value = player.featuredStats.regularSeason.subSeason.plusMinus
                    )
                    StatItem(
                        label = "PIM",
                        value = player.featuredStats.regularSeason.subSeason.pim
                    )
                }
            }
        }
        HorizontalDivider(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        PlayerStatCard(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            player = player
        )
        HorizontalDivider(modifier = modifier.padding(horizontal = 16.dp))
        PlayerInfoCard(
            modifier = modifier.padding(16.dp),
            player = player,
        )
    }
}

/**
 * Purpose - player info card - displays a players personal information
 * @param modifier: The modifier to use
 * @param player: The player to handle
 * @return Unit
 */
@Composable
fun PlayerInfoCard(
    modifier: Modifier,
    player: Player
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            PlayerInfoItem("Height", "${player.heightInCentimeters} cm")
            PlayerInfoItem("Weight", "${player.weightInPounds} lb")
            PlayerInfoItem(
                "Born",
                "${player.birthCity?.default}, " +
                        "${player.birthStateProvince?.default}, " +
                        player.birthCountry
            )
            PlayerInfoItem("Shot", player.shootsCatches)
            PlayerInfoItem("Birthdate", player.birthDate)
            PlayerInfoItem("Draft", "${player.draftDetails.year}, " +
                    "${player.draftDetails.teamAbbrev}, " +
                    "round ${player.draftDetails.round}, " +
                    "pick ${player.draftDetails.pickInRound}")
        }
    }
}

/**
 * Purpose - player info item - an item to display player information
 * @param label: The type of information
 * @param value: The value to display
 * @param color: The color of the value to display
 * @return Unit
 */
@Composable
fun PlayerInfoItem(
    label: String,
    value: String,
    color: Color = Color.Black
) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            softWrap = true
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Gray,
            softWrap = true
        )
    }
}

/**
 * Purpose - player stat card - displays player statistics
 * @param modifier: The modifier to use
 * @param player: The player to handle
 * @return Unit
 */
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
        Text(
            modifier = Modifier.padding(bottom = 18.dp),
            text = "Player Stats",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val points: Int = player.seasonTotals.lastOrNull()?.points ?: 0
            val gamesPlayed: Int = player.seasonTotals.lastOrNull()?.gamesPlayed ?: 0
            val shootingPctg: Int = (
                    (player.seasonTotals.lastOrNull()?.shootingPctg ?: 0.0) * 100).toInt()
            val pointsPerGame: Double = if (gamesPlayed > 0) {
                points.toDouble() / gamesPlayed
            } else {
                0.0
            }
            val shotsPerGame: Double = if (gamesPlayed > 0) {
                (player.seasonTotals.lastOrNull()?.shots?.toDouble() ?: 0.0) / gamesPlayed
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
                label = "Shooting %",
                value = "${shootingPctg}%",
                isGood = shootingPctg > 15
            )
        }
    }
}