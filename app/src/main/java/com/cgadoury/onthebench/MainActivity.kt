package com.cgadoury.onthebench

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cgadoury.onthebench.api.GameManager
import com.cgadoury.onthebench.api.RosterManager
import com.cgadoury.onthebench.api.StandingsViewModel
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.destinations.Destination
import com.cgadoury.onthebench.navigation.BottomNavBar
import com.cgadoury.onthebench.screens.GamesScreen
import com.cgadoury.onthebench.screens.PlayersScreen
import com.cgadoury.onthebench.screens.TeamDetailScreen
import com.cgadoury.onthebench.screens.TeamsScreen
import com.cgadoury.onthebench.ui.theme.OnTheBenchTheme

class MainActivity : ComponentActivity() {
    val standingsViewModel = StandingsViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnTheBenchTheme {
                val navController = rememberNavController()
                val rosterManager = RosterManager("WPG")
                val gameManager = GameManager()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "OnTheBench") }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) { paddingValues ->
                    paddingValues.calculateBottomPadding()
                    Spacer(modifier = Modifier.padding(10.dp))
                    NavHost(
                        navController = navController,
                        startDestination = Destination.Teams.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(Destination.Teams.route) {
                            TeamsScreen(
                                modifier = Modifier,
                                standingsViewModel = standingsViewModel,
                                navController = navController
                            )
                        }

                        composable(Destination.Players.route) {
                            PlayersScreen(rosterManager = rosterManager)
                        }

                        composable(Destination.Games.route) {
                            GamesScreen(gameManager = gameManager)
                        }

                        composable(Destination.TeamDetail.route) { navBackStackEntry ->
                            var team by remember {
                                mutableStateOf<Standing?>(null)
                            }

                            team?.let {
                                TeamDetailScreen(
                                    modifier = Modifier,
                                    team = team!!,
                                    standingsViewModel = standingsViewModel
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
