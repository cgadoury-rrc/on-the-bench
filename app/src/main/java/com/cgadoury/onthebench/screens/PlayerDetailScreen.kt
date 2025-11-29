package com.cgadoury.onthebench.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.stat.StatData
import com.cgadoury.onthebench.db.AppDatabase
import com.cgadoury.onthebench.mvvm.PlayersViewModel
import com.cgadoury.onthebench.ui.components.HorizontalInfoItem
import com.cgadoury.onthebench.ui.components.StatItem
import com.cgadoury.onthebench.ui.components.StatCardRow
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.loadSvgImage
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Purpose - player detail screen - show details for a specific player
 * @param modifier: The application modifier
 * @param player: The player to handle
 * @return Unit
 */
@Composable
fun PlayerDetailScreen(
    modifier: Modifier,
    player: Player,
    playersViewModel: PlayersViewModel,
    db: AppDatabase,
    fsDb: FirebaseFirestore
) {
    val teamAbbrev = player.currentTeamAbbrev
    val teamColor = TeamColors.colors[teamAbbrev] ?: Color.White
    val iconState by playersViewModel.isFavouriteIconState.collectAsState()
    val isIconChanged = iconState[player.playerId] ?: player.isFavourite

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {

        }
        item {
            PlayerHeader(
                modifier = modifier,
                player = player,
                teamColor = teamColor
            )
        }

        item {
            HorizontalDivider(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Player Stats",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        playersViewModel.updateIsFavouriteState(player.playerId, db)

                        var playerExists: Boolean? = null
                        val collection: CollectionReference = fsDb.collection("players")

                        playersViewModel.viewModelScope.launch(Dispatchers.IO) {
                            playerExists = doesPlayerExist(
                                playerId = player.playerId,
                                collection = collection
                            )
                            if (!playerExists && !isIconChanged) {
                                playersViewModel.saveFavouritePlayer(player = player)
                            } else if (playerExists && isIconChanged) {
                                playersViewModel.deleteFavouritePlayer(player.playerId.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isIconChanged) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Is Favourite",
                        tint=Color.Red
                    )
                }
            }
        }

        item {
            PlayerStatColumn(
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                player = player
            )
        }

        item {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Player Info",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            PlayerInfoCard(
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                player = player,
            )
        }
    }
}

/**
 * Purpose - player header - displays player headshot, team, and stat info
 * @param modifier: The application modifier
 * @param player: The player to display
 * @param teamColor: The player's team color
 * @return Unit
 */
@Composable
fun PlayerHeader(
    modifier: Modifier,
    player: Player,
    teamColor: Color
) {
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
        Row(
            modifier = modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier,
                text = "#${player.sweaterNumber}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.4f),
                        offset = Offset(1f, 2f),
                        blurRadius = 3f
                    )
                ),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.95f))
                    .border(1.5.dp, Color.Black.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(44.dp),
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(player.teamLogo)
                        .build(),
                    contentDescription = "Team Logo",
                    imageLoader = loadSvgImage(
                        context = LocalContext.current
                    )
                )
            }
        }
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
                imageLoader = loadSvgImage(context = LocalContext.current)
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
}

/**
 * Purpose - player stat card - displays player statistics
 * @param modifier: The modifier to use
 * @param player: The player to handle
 * @return Unit
 */
@Composable
fun PlayerStatColumn(
    modifier: Modifier,
    player: Player
) {
    Column(
        modifier = modifier
    ) {
        val toi = player.seasonTotals.lastOrNull()?.avgToi ?: ""
        val points: Int = player.seasonTotals.lastOrNull()?.points ?: 0
        val gamesPlayed: Int = player.seasonTotals.lastOrNull()?.gamesPlayed ?: 0
        val gwg = player.seasonTotals.lastOrNull()?.gameWinningGoals ?: 0
        val pointsPerGame: Double = if (gamesPlayed > 0) {
            points.toDouble() / gamesPlayed
        } else {
            0.0
        }
        val goalsPerGame: Double = if (gamesPlayed > 0) {
            (player.seasonTotals.lastOrNull()?.goals?.toDouble() ?: 0.0)/ gamesPlayed
        } else {
            0.0
        }
        val offenseData = listOf<StatData>(
            StatData(
                label = "P/GP",
                value = String.format("%.2f", pointsPerGame),
                isGood = pointsPerGame > 0.75
            ),
            StatData(
                label = "G/GP",
                value = String.format("%.2f", goalsPerGame),
                isGood = goalsPerGame > 0.25
            ),
            StatData(
                label = "GWG",
                value = gwg.toString(),
                isGood = gwg > 0
            )
        )
        StatCardRow(
            modifier = modifier,
            title = "Offense",
            stats = offenseData
        )

        val shots = player.seasonTotals.lastOrNull()?.shots
        val shotsPerGame: Double = if (gamesPlayed > 0) {
            (shots ?: 0.0).toDouble() / gamesPlayed
        } else {
            0.0
        }
        val shootingPctg: Int = (
                (player.seasonTotals.lastOrNull()?.shootingPctg ?: 0.0) * 100
                ).toInt()
        val shootingData = listOf<StatData>(
            StatData(
                label = "Shots",
                value = shots.toString(),
                isGood = shotsPerGame > 2
            ),
            StatData(
                label = "S/GP",
                value = String.format("%.2f", shotsPerGame),
                isGood = shotsPerGame > 2
            ),
            StatData(
                label = "Shooting %",
                value = "${shootingPctg}%",
                isGood = shootingPctg > 9.5
            )
        )
        StatCardRow(
            modifier = modifier,
            title = "Shooting",
            stats = shootingData
        )

        val ppPoints = player.seasonTotals.lastOrNull()?.powerPlayPoints ?: 0
        val shPoints = player.seasonTotals.lastOrNull()?.shorthandedPoints ?: 0
        val foPctg =
            player
                .seasonTotals
                .lastOrNull()
                ?.faceoffWinningPctg
                ?.times(100) ?: 0.0

        val specialTeamsData = listOf<StatData>(
            StatData(
                label = "PP Points",
                value = ppPoints.toString(),
                isGood = ppPoints > 5.5
            ),
            StatData(
                label = "SH Points",
                value = shPoints.toString(),
                isGood = shPoints > 2.5
            ),
            StatData(
                label = "FO %",
                value = "${foPctg.toInt().coerceIn(0, 99)}%",
                isGood = foPctg > 49.5
            )
        )
        StatCardRow(
            modifier = modifier,
            title = "Special Teams",
            stats = specialTeamsData
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
    val playerBirthStateProvince: String? = player.birthStateProvince?.default
    Column(modifier = modifier) {
        HorizontalInfoItem("Height", "${player.heightInCentimeters} cm")
        HorizontalInfoItem("Weight", "${player.weightInPounds} lb")
        HorizontalInfoItem(
            "Born",
            "${player.birthCity?.default}, " +
                    "${playerBirthStateProvince ?: ""} " +
                    player.birthCountry
        )
        HorizontalInfoItem("Shot", player.shootsCatches)
        HorizontalInfoItem("Birthdate", player.birthDate)
        HorizontalInfoItem("Draft",
            "${player.draftDetails.year}, " +
                    "${player.draftDetails.teamAbbrev}, " +
                    "Round ${player.draftDetails.round}, " +
                    "Pick ${player.draftDetails.pickInRound}")
    }
}

/**
 * Purpose - does player exist - checks the firestore db to see if a player exists
 * @param playerId: The player id to search for
 * @param collection: The reference to the firestore collection
 * @return Boolean: True if the player exists in the collection; otherwise false
 */
suspend fun doesPlayerExist(
    playerId: Int,
    collection: CollectionReference
): Boolean {
    val querySnapshot = collection.whereEqualTo("playerId", playerId).get().await()
    return !querySnapshot.isEmpty
}