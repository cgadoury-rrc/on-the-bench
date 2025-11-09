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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cgadoury.onthebench.api.GameManager
import com.cgadoury.onthebench.api.NhlApi
import com.cgadoury.onthebench.api.RosterManager
import com.cgadoury.onthebench.viewmodel.TeamsViewModel
import com.cgadoury.onthebench.db.AppDatabase
import com.cgadoury.onthebench.destinations.Destination
import com.cgadoury.onthebench.navigation.BottomNavBar
import com.cgadoury.onthebench.repository.TeamRepository
import com.cgadoury.onthebench.repository.TeamRepositoryImpl
import com.cgadoury.onthebench.screens.GamesScreen
import com.cgadoury.onthebench.screens.PlayersScreen
import com.cgadoury.onthebench.screens.TeamDetailScreen
import com.cgadoury.onthebench.screens.TeamsScreen
import com.cgadoury.onthebench.ui.theme.OnTheBenchTheme
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnTheBenchTheme {
                val navController = rememberNavController()
                val rosterManager = RosterManager("WPG")
                val gameManager = GameManager()
                val db = AppDatabase.getInstance(applicationContext)
                val api = NhlApi.retrofitService
                val teamRepository: TeamRepository = TeamRepositoryImpl(api, db.teamDao())

                val teamsViewModel by lazy {
                    TeamsViewModel(teamRepository = teamRepository)
                }

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
                                teamsViewModel = teamsViewModel,
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
                            val team by teamsViewModel.team
                            val teamAbbrev = navBackStackEntry.arguments?.getString("teamAbbrev")

                            LaunchedEffect(teamAbbrev) {
                                teamsViewModel.getTeamByAbbreviation(teamAbbrev = teamAbbrev!!)
                            }

                            team?.let {
                                TeamDetailScreen(
                                    modifier = Modifier,
                                    team = team!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
