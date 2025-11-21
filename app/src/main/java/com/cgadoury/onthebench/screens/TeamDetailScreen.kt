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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.roster.RosterPlayerData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.mvvm.TeamsViewModel
import com.cgadoury.onthebench.ui.components.StatusStatCard
import com.cgadoury.onthebench.ui.components.StatItem
import com.cgadoury.onthebench.ui.theme.TeamColors
import com.cgadoury.onthebench.utility.SvgDecoderUtil

/**
 * Purpose - team detail screen -
 */
@Composable
fun TeamDetailScreen(
    modifier: Modifier,
    team: Standing,
    teamsViewModel: TeamsViewModel,
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val teamAbbrev = team.teamAbbrev.default
        LaunchedEffect(teamAbbrev) {
            teamsViewModel.getCurrentTeamRoster(teamAbbrev = teamAbbrev)
        }
        val teamRoster = teamsViewModel.teamRoster.value
        val teamColor = TeamColors.colors[teamAbbrev] ?: Color.White
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
                    imageLoader = SvgDecoderUtil()
                        .decodeSvgImage(
                            context = LocalContext.current
                        )
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
                    StatItem("GP", team.gamesPlayed)
                    StatItem("W", team.wins)
                    StatItem("L", team.losses)
                    StatItem("OTL", team.otLosses)
                    StatItem("Points", team.points)
                }
            }
        }
        HorizontalDivider(modifier = modifier.padding(16.dp, 8.dp))
        TeamStatCard(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            team = team
        )
        HorizontalDivider(modifier = modifier.padding(horizontal = 16.dp))
        if (teamRoster != null) {
            TeamRosterCard(
                modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                teamRoster = teamRoster,
                navController = navController
            )
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
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Text(
            modifier = Modifier.padding(bottom = 18.dp),
            text = "Team Stats",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val winPctgAsInt = (team.winPctg * 100).toInt()
            val isWinPctgGood = winPctgAsInt > 50
            val isGoalDiffGood = team.goalDifferential > 0
            val isStreakGood = team.streakCode.startsWith("W")

            StatusStatCard(
                modifier = Modifier.weight(1f),
                label ="Win Pctg.",
                value = "${winPctgAsInt}%",
                isGood = isWinPctgGood
            )
            StatusStatCard(
                modifier = Modifier.weight(1f),
                label = "Goal Diff.",
                value = team.goalDifferential.toString(),
                isGood = isGoalDiffGood
            )
            StatusStatCard(
                modifier = Modifier.weight(1f),
                label = "Streak",
                value = team.streakCode + team.streakCount,
                isGood = isStreakGood
            )
        }
    }
}

/**
 * Purpose team roster - display the team roster
 */
@Composable
fun TeamRosterCard(
    modifier: Modifier,
    teamRoster: RosterData,
    navController: NavController
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = "Roster",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        LazyColumn {
            items(teamRoster.forwards) { forward ->
                RosterPlayerCard(
                    modifier = modifier,
                    rosterPlayer = forward,
                    navController = navController
                )
            }
            items(teamRoster.defensemen) {defenseman ->
                RosterPlayerCard(
                    modifier = modifier,
                    rosterPlayer = defenseman,
                    navController = navController
                )
            }
        }
    }

}

@Composable
fun RosterPlayerCard(
    modifier: Modifier,
    rosterPlayer: RosterPlayerData,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = {
            navController.navigate("playerDetail/${rosterPlayer.id}")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .align(Alignment.CenterVertically),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(rosterPlayer.headshot)
                    .build(),
                contentDescription = null,
                imageLoader = SvgDecoderUtil()
                    .decodeSvgImage(
                        context = LocalContext.current
                    )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 26.dp),
                text = rosterPlayer.firstName?.default.toString() +
                    " ${rosterPlayer.lastName?.default.toString()}",
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}