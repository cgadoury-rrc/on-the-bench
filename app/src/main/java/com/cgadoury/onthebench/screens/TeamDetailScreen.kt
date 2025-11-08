package com.cgadoury.onthebench.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.cgadoury.onthebench.api.StandingsViewModel
import com.cgadoury.onthebench.api.model.standing.Standing

@Composable
fun TeamDetailScreen(
    modifier: Modifier,
    team: Standing,
    standingsViewModel: StandingsViewModel
) {
    Box(
        modifier
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(),
                text = team.teamName.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            AsyncImage(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(4.dp),
                model = ImageRequest.Builder(
                    LocalContext.current
                ).data(team.teamLogo)
                    .build(),
                contentDescription = null,
                imageLoader = ImageLoader.Builder(
                    LocalContext.current
                ).components {
                    add(SvgDecoder.Factory())
                }
                    .build()
            )

            Log.i("Team Details", team.toString())
        }
    }

}