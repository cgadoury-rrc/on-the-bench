package com.cgadoury.onthebench.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.api.model.game.Game
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.mvvm.GamesViewModel
import com.cgadoury.onthebench.mvvm.TeamsViewModel
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.loadSvgImage
import com.cgadoury.onthebench.utility.predictGameWinner
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GamesScreen(
    modifier: Modifier,
    gamesViewModel: GamesViewModel,
    teamsViewModel: TeamsViewModel,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize()
    )
    val xFactor = gamesViewModel.wildcard
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
                    xFactor = xFactor,
                    teamsViewModel = teamsViewModel,
                    navController = navController
                )
            }
            1 -> {
                GameDayTab(
                    modifier = Modifier,
                    gameDayData = gamesToday,
                    xFactor = xFactor,
                    teamsViewModel = teamsViewModel,
                    navController = navController
                )
            }
            2 -> {
                GameDayTab(
                    modifier = Modifier,
                    gameDayData = gamesTomorrow,
                    xFactor = xFactor,
                    teamsViewModel = teamsViewModel,
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameDayTab(
    modifier: Modifier,
    gameDayData: List<Game>,
    xFactor: Int,
    teamsViewModel: TeamsViewModel,
    navController: NavController
): Unit {

    LazyColumn {
        items(gameDayData) { game ->
            GameCard(
                modifier = modifier.padding(5.dp),
                game = game,
                teamsViewModel = teamsViewModel,
                xFactor = xFactor,
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameCard(
    modifier: Modifier,
    game: Game,
    xFactor: Int,
    teamsViewModel: TeamsViewModel,
    navController: NavController
): Unit {
    val homeTeam: Standing =
        teamsViewModel.getTeamByAbbreviation(game.homeTeam?.abbrev.toString())
    val awayTeam: Standing =
        teamsViewModel.getTeamByAbbreviation(game.awayTeam?.abbrev.toString())
    val winPrediction = remember(game.id) {
        predictGameWinner(homeTeam = homeTeam, awayTeam = awayTeam, xFactor = xFactor)
    }
    val homePercent = (if (winPrediction.first == homeTeam) winPrediction.second else winPrediction.third).toFloat()
    val awayPercent = (if (winPrediction.first == awayTeam) winPrediction.second else winPrediction.third).toFloat()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        GameInfoRow(game)

        Spacer(Modifier.height(8.dp))

        Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TeamScore(
                score = if (game.awayTeam?.score == null) "0" else game.awayTeam?.score.toString(),
                abbrev = let { game.awayTeam?.abbrev } as String,
                logo = let { game.awayTeam?.logo } as String,
                isScoreOnLeft = false
            )

            GameScoreClock(game = game)

            TeamScore(
                score = if (game.homeTeam?.score == null) "0" else game.homeTeam?.score.toString(),
                abbrev = let { game.homeTeam?.abbrev } as String,
                logo = let { game.homeTeam?.logo } as String,
                isScoreOnLeft = true
            )
        }

        Spacer(Modifier.height(12.dp))

        PredictedWinPercentageBar(
            modifier = Modifier,
            homePercent = homePercent,
            awayPercent = awayPercent,
            homeTeamColor = TeamColors.colors[homeTeam.teamAbbrev.default] ?: Color.White,
            awayTeamColor = TeamColors.colors[awayTeam.teamAbbrev.default] ?: Color.White
        )

        Spacer(Modifier.height(12.dp))
    }
}

/**
 * Purpose - team score - display a team's logo and score
 * @param score: The team's score to display
 * @param abbrev: The team abbreviation
 * @param logo: The team's logo url
 * @param isScoreOnLeft: If true, display's score to the left of the logo; otherwise display
 * score to the right of the logo
 * @return Unit
 */
@Composable
fun TeamScore(
    score: String,
    abbrev: String,
    logo: String,
    isScoreOnLeft: Boolean
): Unit {
    if (isScoreOnLeft) {
        Text(
            text = score,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = abbrev, fontWeight = FontWeight.Bold)
        AsyncImage(
            modifier = Modifier
                .size(75.dp),
            model = ImageRequest.Builder(
                LocalContext.current
            ).data(logo)
                .build(),
            contentDescription = "Team Logo",
            imageLoader = loadSvgImage(context = LocalContext.current)
        )
    }
    if (!isScoreOnLeft) {
        Text(
            text = score,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Purpose - game info row - displays the game start time and game status
 * @param game: The game to display information
 * @return Unit
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameInfoRow(game: Game): Unit {
    val startTime = game.startTimeUTC.toString()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = getFormattedStartDateTimeString(utcString = startTime))
        game.gameState?.let {
            Text(
                text = if (game.gameState == "OFF") "FINAL" else it,
                color = if (game.gameState == "LIVE") Color.Red else Color.DarkGray
            )
        }
    }
}

/**
 * Purpose - game score clock - display period, time remaining, VS text
 * @param game: The game to retrieve score clock details
 * @return Unit
 */
@Composable
fun GameScoreClock(game: Game) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (game.period.toString() != "null") {
            Text(text = "P${game.period.toString()} ")
            Text(text = game.clock?.timeRemaining.toString())
        }
        Text(text="VS")
    }
}

/**
 * Purpose - predicted win percentage bar - display predicted win percentages
 * and colored bar representing each team
 * @param modifier: The application modifier
 * @param homePercent: The home teams predicted chance of winning
 * @param awayPercent: The away teams predicted chance of winning
 * @param homeTeamColor: The home teams main color
 * @param awayTeamColor: The away teams main color
 * @return Unit
 */
@Composable
fun PredictedWinPercentageBar(
    modifier: Modifier,
    homePercent: Float,
    awayPercent: Float,
    homeTeamColor: Color,
    awayTeamColor: Color
): Unit {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${awayPercent.toInt()}%",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "${homePercent.toInt()}%",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(10.dp)
            .clip(RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = modifier
                .weight(maxOf(awayPercent, 10f))
                .fillMaxHeight()
                .background(
                    color = awayTeamColor
                )
        )
        Box(
            modifier = modifier
                .weight(maxOf(homePercent, 10f))
                .fillMaxHeight()
                .background(
                    color = homeTeamColor
                )
        )
    }
}

/**
 * Purpose - get formatted start date time string - formats game start time from utc to local time
 * @param utcString: The game start time in utc as a string
 * @return String: The formatted game start time
 */
@RequiresApi(Build.VERSION_CODES.O)
private fun getFormattedStartDateTimeString(utcString: String): String {
    val instant = Instant.parse(utcString)
    val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
    return localDateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd - h:mm a"))
}