package com.cgadoury.onthebench

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cgadoury.onthebench.api.GameManager
import com.cgadoury.onthebench.api.RosterManager
import com.cgadoury.onthebench.api.StandingManager
import com.cgadoury.onthebench.ui.theme.OnTheBenchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnTheBenchTheme {
                // Pass to RosterScreen
                val rosterManager = RosterManager("WPG")

                // Pass to StandingScreen
                val standingManager = StandingManager()

                // Pass to GameScreen
                val gameManager = GameManager()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> }
            }
        }
    }
}
