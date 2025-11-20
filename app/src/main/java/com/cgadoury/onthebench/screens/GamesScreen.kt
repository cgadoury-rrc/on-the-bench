package com.cgadoury.onthebench.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.api.model.game.Game
import com.cgadoury.onthebench.mvvm.GamesViewModel
import com.cgadoury.onthebench.utility.SvgDecoderUtil
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Purpose - games screen - display nhl games
 * @param modifier: The modifier to use
 * @param gamesViewModel: The games view model
 * @param navController: The application navigation controller
 * @return Unit
 */
@Composable
fun GamesScreen(
    modifier: Modifier,
    gamesViewModel: GamesViewModel,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize()
    )

    val gamesToday by gamesViewModel.gamesTodayResponse
    val gamesYesterday by gamesViewModel.gamesYesterdayResponse
    val gamesTomorrow by gamesViewModel.gamesTomorrowResponse

    var selectedTabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("Yesterday", "Today", "Tomorrow")

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> {
                GameDayTab(
                    modifier = Modifier,
                    gameDayData = gamesYesterday,
                    navController = navController
                )
            }
            1 -> {
                GameDayTab(
                    modifier = Modifier,
                    gameDayData = gamesToday,
                    navController = navController
                )
            }
            2 -> {
                GameDayTab(
                    modifier = Modifier,
                    gameDayData = gamesTomorrow,
                    navController = navController
                )
            }
        }
    }
}

/**
 * Purpose - game day tab - displays game day data for a given day
 * @param modifier: The application modifier
 * @param gameDayData: Game day data to process (yesterday, today, tomorrow)
 * @param navController: The application navigation controller
 * @return Unit
 */
@Composable
fun GameDayTab(
    modifier: Modifier,
    gameDayData: List<Game>,
    navController: NavController
): Unit {
    LazyColumn {
        items(gameDayData) { game ->
            GameCard(
                modifier = modifier.padding(5.dp),
                game = game,
                navController = navController
            )
        }
    }
}

/**
 * Purpose - game card - a game item in the games screen
 * @param modifier: The modifier to use
 * @param game: The game to handle
 * @param navController: The application navigation controller
 * @return Unit
 */
@Composable
fun GameCard(
    modifier: Modifier,
    game: Game,
    navController: NavController
): Unit {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        val utcString = game.startTimeUTC.toString()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val isLive: Boolean = game.gameOutcome == null
            Text(text = getFormattedStartDateTimeString(utcString = utcString))
            Text(
                text = if (isLive) "LIVE •" else "FINAL",
                color = if (isLive) Color.Red else Color.DarkGray
                )
        }
        Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(12.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                game.awayTeam?.abbrev?.let { Text(text = it, fontWeight = FontWeight.Bold) }
                AsyncImage(
                    modifier = Modifier
                        .size(75.dp),
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(game.awayTeam?.logo)
                        .build(),
                    contentDescription = "Away Team Logo",
                    imageLoader = SvgDecoderUtil()
                        .decodeSvgImage(
                            context = LocalContext.current
                        )
                )
            }
            val awayTeamScore =
                if (game.awayTeam?.score.toString() == "null") "0"
                else game.awayTeam?.score.toString()
            Text(
                text = awayTeamScore,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "P${game.period.toString()} ")
                Text(text = game.clock?.timeRemaining.toString())
                Text(text="VS")
            }

            val homeTeamScore =
                if (game.homeTeam?.score.toString() == "null") "0"
                else game.homeTeam?.score.toString()
            Text(
                text = homeTeamScore,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                game.homeTeam?.abbrev?.let { Text(text = it, fontWeight = FontWeight.Bold) }
                AsyncImage(
                    modifier = Modifier
                        .size(75.dp),
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(game.homeTeam?.logo)
                        .build(),
                    contentDescription = "Home Team Logo",
                    imageLoader = SvgDecoderUtil()
                        .decodeSvgImage(
                            context = LocalContext.current
                        )
                )
            }
        }
    }
}

/**
 * Purpose - get formatted start date time string - formats game start time from utc to local time
 * @param utcString: The game start time in utc as a string
 * @return String: The formatted game start time
 */
private fun getFormattedStartDateTimeString(utcString: String): String {
    val instant = Instant.parse(utcString)
    val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
    return localDateTime.format(DateTimeFormatter.ofPattern("MMM dd - h:mm a"))
}