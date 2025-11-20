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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.svg.SvgDecoder
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.mvvm.TeamsViewModel
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.utility.SvgDecoderUtil

/**
 * Purpose - Teams Screen - display NHL teams for the current season
 * @author Colton Gadoury
 * @param modifier: The modifier for the screen
 * @param teamsViewModel: The view model where initial team data is retrieved
 * @constructor Emits a new Teams Screen composable
 * @return Unit
 */
@Composable
fun TeamsScreen(
    modifier: Modifier,
    teamsViewModel: TeamsViewModel,
    navController: NavController
){
    Box(
        modifier = modifier.fillMaxSize()
    )

    val teams by teamsViewModel.standingsResponse

    LazyColumn {
        items(teams) { team ->
            TeamCard(
                modifier = modifier.padding(5.dp),
                team = team,
                navController = navController
            )
        }
    }
}

/**
 * Purpose - Team Card - display a team
 * @author Colton Gadoury
 * @param modifier: The modifier
 * @param team: The team to display
 * @constructor Emits a new Team Card composable
 * @return Unit
 */
@Composable
fun TeamCard(
    modifier: Modifier,
    team: Standing?,
    navController: NavController
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = {
            navController.navigate("teamDetail/${team?.teamAbbrev?.default}")
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
                ).data(team?.teamLogo)
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
                    text = team?.teamName?.default.orEmpty(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "L10 ")
                    Text(text = "W: ${team?.l10Wins} ")
                    Text(text = "L: ${team?.l10Losses} ")
                    Text(text = "OTL: ${team?.l10OtLosses}")
                }
            }
        }
    }
}