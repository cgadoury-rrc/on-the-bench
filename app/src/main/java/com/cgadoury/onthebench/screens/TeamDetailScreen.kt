package com.cgadoury.onthebench.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.api.model.roster.RosterPlayerData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.stat.StatData
import com.cgadoury.onthebench.mvvm.TeamsViewModel
import com.cgadoury.onthebench.ui.components.StatItem
import com.cgadoury.onthebench.ui.components.StatCardRow
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.loadSvgImage

/**
 * Purpose - team detail screen - display team details including stats and rosters
 * @param modifier: The application modifier
 * @param team: The team to display details from
 * @param teamsViewModel: The teams view model to retrieve team information
 * @param navController: The application navigation controller
 */
@Composable
fun TeamDetailScreen(
    modifier: Modifier,
    team: Standing,
    teamsViewModel: TeamsViewModel,
    navController: NavController
) {
    val teamAbbrev = team.teamAbbrev.default
    LaunchedEffect(teamAbbrev) {
        teamsViewModel.getCurrentTeamRoster(teamAbbrev = teamAbbrev)
    }
    val teamRoster = teamsViewModel.teamRoster.value
    val teamColor = TeamColors.colors[teamAbbrev] ?: Color.White
    val iconState by teamsViewModel.isFavouriteIconState.collectAsState()
    val isIconChanged = iconState[teamAbbrev] ?: team.isFavourite

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            TeamHeader(
                modifier = modifier,
                teamColor = teamColor,
                team = team
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
                    text = "Team Stats",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = {
                        teamsViewModel.updateIsFavouriteTeamState(teamAbbrev = teamAbbrev)

                        if (!isIconChanged) {
                            teamsViewModel.saveFavouriteTeam(team = team)
                        } else {
                            teamsViewModel.deleteFavouriteTeam(teamAbbrev = teamAbbrev)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isIconChanged) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Is favourite team",
                        tint=Color.Red
                    )
                }
            }
        }

        item {
            TeamStatCard(
                modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                team = team
            )
        }

        if (teamRoster != null) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Roster",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Forwards",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            items(teamRoster.forwards) { forward ->
                RosterPlayerRow(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    rosterPlayer = forward,
                    navController = navController
                )
            }

            item {
                Text(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Defenseman",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            items(teamRoster.defensemen) {defenseman ->
                RosterPlayerRow(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    rosterPlayer = defenseman,
                    navController = navController
                )
            }

            item {
                Text(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Goalies",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            items(teamRoster.goalies) {goalie ->
                RosterPlayerRow(
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    rosterPlayer = goalie,
                    navController = navController
                )
            }
        }
    }
}

/**
 * Purpose - team header - display current team information
 * @param modifier: The application modifier
 * @param teamColor: The designated team color
 * @param team: The team to display
 */
@Composable
fun TeamHeader(
    modifier: Modifier,
    teamColor: Color,
    team: Standing
) {
    val backgroundColors = listOf(
        teamColor,
        teamColor.copy(alpha = 0.8f),
        teamColor.copy(alpha = 0.5f),
        teamColor.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = modifier
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
                    .clip(CircleShape)
                    .background(teamColor),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(team.teamLogo)
                    .build(),
                contentDescription = null,
                imageLoader = loadSvgImage(context = LocalContext.current)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = team.teamName.default.orEmpty(),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical=8.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = "GP",
                    value = team.gamesPlayed
                )
                StatItem(
                    label = "W",
                    value = team.wins
                )
                StatItem(
                    label = "L",
                    value = team.losses
                )
                StatItem(
                    label = "OTL",
                    value = team.otLosses
                )
                StatItem(
                    label = "Points",
                    value = team.points
                )
            }
        }
    }
}

/**
 * Purpose - team stat card - display team statistics
 * @param modifier: The application modifier
 * @param team: The team to display statistics for
 * @return Unit
 */
@Composable
fun TeamStatCard(
    modifier: Modifier,
    team: Standing,
) {
    Column(
        modifier = modifier
    ) {
        val hasPlayedGames = team.gamesPlayed > 0
        val overallWinPctg = (team.winPctg * 100).toInt()
        val overallStatData = listOf<StatData>(
            StatData(
                label = "Win %",
                value = "$overallWinPctg%",
                isGood = overallWinPctg > 50
            ),
            StatData(
                label = "Goal Diff.",
                value = team.goalDifferential.toString(),
                isGood = team.goalDifferential > 0
            ),
            StatData(
                label = "Streak",
                value = team.streakCode + team.streakCount,
                isGood = team.streakCode.startsWith("W")
            )
        )

        StatCardRow(
            modifier = modifier,
            title = "Overall",
            stats = overallStatData
        )

        val homeWinPctg: Double = if (hasPlayedGames && team.wins > 0) {
            team.homeWins / team.wins.toDouble() * 100
        } else {
            0.0
        }
        val homeGoalsForPerGame: Double = if (hasPlayedGames) {
            team.homeGoalsFor / team.homeGamesPlayed.toDouble()
        } else {
            0.0
        }
        val homeGoalsAgainstPerGame: Double = if (hasPlayedGames) {
            team.homeGoalsAgainst / team.homeGamesPlayed.toDouble()
        } else {
            0.0
        }
        val homeStatData = listOf(
            StatData(
                label = "Home Win %",
                value = "${homeWinPctg.toInt()}%",
                isGood = homeWinPctg.toInt() > 49.5
            ),
            StatData(
                label = "GF/GP",
                value = String.format("%.2f", homeGoalsForPerGame),
                isGood = homeGoalsForPerGame > 2.5
            ),
            StatData(
                label = "GA/GP",
                value = String.format("%.2f", homeGoalsAgainstPerGame),
                isGood = homeGoalsAgainstPerGame < 2.5
            )
        )

        StatCardRow(
            modifier = modifier,
            title = "Home",
            stats = homeStatData
        )

        val awayGoalsForPerGame: Double = if (hasPlayedGames) {
            team.roadGoalsFor / team.roadGamesPlayed.toDouble()
        } else {
            0.0
        }
        val awayGoalsAgainstPerGame: Double = if (hasPlayedGames) {
            team.roadGoalsAgainst / team.roadGamesPlayed.toDouble()
        } else {
            0.0
        }
        val awayStatData: List<StatData> = listOf(
            StatData(
                label = "Away Win %",
                value = "${100 - homeWinPctg.toInt()}%",
                isGood = 100 - homeWinPctg.toInt() > 49.5
            ),
            StatData(
                label = "GF/GP",
                value = String.format("%.2f", awayGoalsForPerGame),
                isGood = awayGoalsForPerGame > 3
            ),
            StatData(
                label = "GA/GP",
                value = String.format("%.2f", awayGoalsAgainstPerGame),
                isGood = awayGoalsAgainstPerGame < 2.5
            )
        )

        StatCardRow(
            modifier = modifier,
            title = "Away",
            stats = awayStatData
            )
    }
}

/**
 * Purpose - roster player row - display a roster player row
 * @param modifier: The application modifier
 * @param rosterPlayer: Roster player information
 * @param navController: The application navigation controller
 * @return Unit
 */
@Composable
fun RosterPlayerRow(
    modifier: Modifier,
    rosterPlayer: RosterPlayerData,
    navController: NavController
): Unit {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navController.navigate("playerDetail/${rosterPlayer.id}") },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.3f))
                .align(Alignment.CenterVertically),
            model = ImageRequest.Builder(
                LocalContext.current
            ).data(rosterPlayer.headshot)
                .build(),
            contentDescription = null,
            imageLoader = loadSvgImage(context = LocalContext.current)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            val fullName = rosterPlayer.firstName?.default.toString() +
                    " ${rosterPlayer.lastName?.default.toString()}"

            Text(
                text = fullName,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "#${rosterPlayer.sweaterNumber} - ${rosterPlayer.positionCode}",
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 12.dp),
        color = Color.Gray.copy(alpha = 0.3f)
    )
}