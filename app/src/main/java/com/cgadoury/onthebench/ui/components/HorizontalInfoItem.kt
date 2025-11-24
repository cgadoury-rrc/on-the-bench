package com.cgadoury.onthebench.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Purpose - info item - an item to display detailed information
 * @param label: The type of information
 * @param value: The value to display
 * @param color: The color of the value to display
 * @return Unit
 */
@Composable
fun HorizontalInfoItem(
    label: String,
    value: String,
    color: Color = Color.Black
) {
    Row(
        modifier = Modifier.padding(bottom = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            softWrap = true
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.DarkGray,
            softWrap = true
        )
    }
}