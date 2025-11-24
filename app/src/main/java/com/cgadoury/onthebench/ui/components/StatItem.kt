package com.cgadoury.onthebench.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Purpose - stat item - display a statistic and corresponding label
 * @param label: A label describing the statistic
 * @param value: The value of the statistic
 * @param color: The text color of the value
 * @return
 */
@Composable
fun StatItem(
    label: String,
    value: Int?,
    color: Color=Color.Black
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value?.toString() ?: "0",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 16.sp,
            color = color
        )
    }
}