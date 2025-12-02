package com.cgadoury.onthebench.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cgadoury.onthebench.api.model.stat.StatData

/**
 * Purpose - stat card row - displays a row of stat cards
 * @param modifier: The application modifier
 * @param title: The title of the stat card row
 * @param stats: A list of stat data to display
 * @return Unit
 */
@Composable
fun StatCardRow(
    modifier: Modifier,
    title: String,
    stats: List<StatData>
) {
    Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.headlineSmall
    )
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        stats.forEach { statData ->
            StatusStatCard(
                modifier = Modifier.weight(1f),
                label = statData.label,
                value = statData.value,
                isGood = statData.isGood
            )
        }
    }
}