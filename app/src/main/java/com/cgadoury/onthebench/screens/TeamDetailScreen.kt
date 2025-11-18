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
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.ui.components.StatusStatCard
import com.cgadoury.onthebench.ui.components.StatItem
import com.cgadoury.onthebench.ui.theme.TeamColors

/**
 *
 */
@Composable
fun TeamDetailScreen(
    modifier: Modifier,
    team: Standing,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val teamAbbrev = team.teamAbbrev.default
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
                    imageLoader = ImageLoader.Builder(
                        LocalContext.current
                    ).components {
                        add(SvgDecoder.Factory())
                    }.build()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = team.teamName.default.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium,
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

        HorizontalDivider(modifier = modifier.padding(16.dp, 12.dp, 16.dp, 0.dp), thickness = 1.dp)

        TeamStatCard(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            team = team
        )

        HorizontalDivider(modifier = modifier.padding(horizontal = 16.dp))
    }
}


/**
 *
 */
@Composable
fun TeamStatCard(
    modifier: Modifier,
    team: Standing
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
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