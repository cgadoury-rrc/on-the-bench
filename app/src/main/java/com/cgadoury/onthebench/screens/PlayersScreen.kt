package com.cgadoury.onthebench.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.mvvm.PlayersViewModel
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.loadSvgImage
import kotlin.collections.get

/**
 * Purpose - players screen - displays top 50 nhl players
 * @param modifier: The modifier to use
 * @param playersViewModel: The players view model
 * @param navController: The navigation controller
 * @return Unit
 */
@Composable
fun PlayersScreen(
    modifier: Modifier,
    playersViewModel: PlayersViewModel,
    navController: NavController
) {
    playersViewModel.getFavouritePlayers()

    val pointsLeaders by playersViewModel.topPlayersResponse
    val favouritePlayers by playersViewModel.favouritePlayersResponse
    val hasFavourites = !favouritePlayers.isEmpty()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "Favourite Players",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        item {
            FavouritePlayerRow(
                modifier = modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                favouritePlayers = favouritePlayers,
                navController = navController
            )
        }

        item {
            HorizontalDivider(modifier = modifier.padding(horizontal = 12.dp, vertical = 24.dp))
        }

        item {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                text = "Points Leaders",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(pointsLeaders) { player ->
            PlayerCard(
                modifier = modifier.padding(6.dp),
                player = player,
                navController = navController
            )
        }
    }
}

/**
 * Purpose - player card - a player item in the players screen
 * @param modifier: The modifier to use
 * @param player: The player to handle
 * @param navController: The navigation controller
 */
@Composable
fun PlayerCard(
    modifier: Modifier,
    player: Point?,
    navController: NavController
) {
    val teamColor = TeamColors.colors[player?.teamAbbrev] ?: Color.White
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, teamColor),
        colors = CardDefaults.cardColors(
            containerColor = teamColor.copy(alpha = 0.03f)
        ),
        onClick = {
            navController.navigate("playerDetail/${player?.id}")
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(player?.headshot)
                    .build(),
                contentDescription = null,
                imageLoader = loadSvgImage(
                        context = LocalContext.current
                    )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = ("${player?.firstName?.default} ${player?.lastName?.default}"),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "#${player?.sweaterNumber}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    VerticalDivider(
                        modifier= Modifier
                            .height(20.dp)
                            .padding(horizontal = 20.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )

                    AsyncImage(
                        modifier = Modifier
                            .size(40.dp),
                        model = ImageRequest.Builder(
                            LocalContext.current
                       ).data(player?.teamLogo)
                            .build(),
                        contentDescription = "Player Team Logo",
                        imageLoader = loadSvgImage(
                                context = LocalContext.current
                            )
                    )

                    VerticalDivider(
                        modifier= Modifier
                            .height(20.dp)
                            .padding(horizontal = 20.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )

                    Text(
                        text = "${player?.position}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Purpose - favourite row - dynamically displays favourite players
 * @param modifier: The application modifier
 * @param favouritePlayers: The list of players to display
 * @param navController: The application navigation controller
 * @return Unit
 */
@Composable
fun FavouritePlayerRow(
    modifier: Modifier,
    favouritePlayers: List<Player>,
    navController: NavController
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        maxItemsInEachRow = 3
    ) {
        favouritePlayers.forEach { player ->
            val playerLastName = player.lastName?.default.toString()
            val teamColor = TeamColors.colors[player.currentTeamAbbrev]

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(teamColor!!.copy(alpha = 0.8f))
                        .border(
                            BorderStroke(width = 2.dp, teamColor),
                            shape = CircleShape
                        )
                        .clickable {
                            navController.navigate("playerDetail/${player.playerId}")
                                   },
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(player.headshot)
                        .build(),
                    contentDescription = "$playerLastName Headshot",
                    imageLoader = loadSvgImage(
                        context = LocalContext.current
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = playerLastName,
                    fontSize = 14.sp,
                    letterSpacing = 2.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}