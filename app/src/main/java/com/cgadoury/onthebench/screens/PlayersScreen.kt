package com.cgadoury.onthebench.screens

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
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.mvvm.PlayersViewModel

@Composable
fun PlayersScreen(
    modifier: Modifier,
    playersViewModel: PlayersViewModel,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize()
    )

    val topPlayers by playersViewModel.topPlayersResponse

    LazyColumn {
        items(topPlayers) { player ->
            PlayerCard(
                modifier = modifier.padding(5.dp),
                player = player,
                navController = navController
            )
        }
    }
}

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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(player?.headshot)
                    .build(),
                contentDescription = null,
                imageLoader = ImageLoader.Builder(
                    LocalContext.current
                ).components {
                    add(SvgDecoder.Factory())
                }
                    .build()
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
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "#${player?.sweaterNumber}")
                    Text(text = "${player?.teamName?.default}")
                    Text(text = "${player?.position}")
                }
            }
        }
    }
}