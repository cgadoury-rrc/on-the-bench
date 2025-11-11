package com.cgadoury.onthebench.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.model.standing.Standing

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
        AsyncImage(
            modifier = Modifier
                .size(85.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.1f))
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("GP", team.gamesPlayed, Color.Black)
            StatItem("W", team.wins, Color.Black)
            StatItem("L", team.losses, Color.Black)
            StatItem("OTL", team.otLosses, Color.Black)
            StatItem("Points", team.points, Color.Black)
        }

        OverallStatCard(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            teamData = team
        )
    }
}

/**
 *
 */
@Composable
fun StatItem(label: String, value: Int?, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value?.toString() ?: "0",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

/**
 *
 */
@Composable
fun OverallStatCard(
    modifier: Modifier,
    teamData: Standing
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
            LargeStatPctgCard(Modifier.weight(1f), "Win Pctg.", teamData.winPctg)
            LargeStatGoalDiffCard(Modifier.weight(1f), "Goal Diff.", teamData.goalDifferential)
            LargeStatStreakCard(Modifier.weight(1f), "Streak", teamData.streakCode + teamData.streakCount)
        }
    }
}

/**
 *
 */
@Composable
fun LargeStatPctgCard(
    modifier: Modifier,
    label: String,
    value: Double,
) {
    val pctgAsInt = (value * 100).toInt()
    Log.i("Pctg", pctgAsInt.toString())
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Card(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (pctgAsInt > 50) Color(0xFF4C70AF) else Color(0xFFF44336)
            )
        ) {
            Text(
                text = "${pctgAsInt}%",
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 *
 */
@Composable
fun LargeStatStreakCard(
    modifier: Modifier,
    label: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Card(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (value.startsWith("W")) Color(0xFF4C70AF) else Color(0xFFF44336)
            )
        ) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LargeStatGoalDiffCard(
    modifier: Modifier,
    label: String,
    value: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Card(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (value > 0) Color(0xFF4C70AF) else Color(0xFFF44336)
            )
        ) {
            Text(
                text = if (value > 0) "+${value}" else value.toString(),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}