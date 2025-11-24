package com.cgadoury.onthebench.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Purpose - status stat card - display a statistic with a colored card
 * @param modifier: The modifier to use
 * @param label: A label describing the statistic
 * @param value: The value of the statistic
 * @param isGood: Determines if the statistic is good (blue) or bad (red)
 * @return Unit
 */
@Composable
fun StatusStatCard(
    modifier: Modifier,
    label: String,
    value: String,
    isGood: Boolean
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isGood) Color(0xFF81C784) else Color(0xFFFFAB91)
            )
        ) {
            Text(
                text = value,
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}