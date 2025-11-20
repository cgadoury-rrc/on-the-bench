package com.cgadoury.onthebench.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.mvvm.PlayersViewModel
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.SvgDecoderUtil
import org.jetbrains.annotations.Async

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
    Box(
        modifier = modifier.fillMaxSize()
    )
    val pointsLeaders by playersViewModel.topPlayersResponse

    LazyColumn {
        items(pointsLeaders) { player ->
            PlayerCard(
                modifier = modifier.padding(5.dp),
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
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(player?.headshot)
                    .build(),
                contentDescription = null,
                imageLoader = SvgDecoderUtil()
                    .decodeSvgImage(
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
                        fontSize = 18.sp
                    )
                    VerticalDivider(
                        modifier= Modifier
                            .height(20.dp)
                            .padding(horizontal = 16.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )
                    AsyncImage(
                        modifier = Modifier
                            .size(35.dp),
                        model = ImageRequest.Builder(
                            LocalContext.current
                       ).data(player?.teamLogo)
                            .build(),
                        contentDescription = "Team Logo",
                        imageLoader = SvgDecoderUtil()
                            .decodeSvgImage(
                                context = LocalContext.current
                            )
                    )
                    VerticalDivider(
                        modifier= Modifier
                            .height(20.dp)
                            .padding(horizontal = 16.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )
                    Text(
                        text = "${player?.position}",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}