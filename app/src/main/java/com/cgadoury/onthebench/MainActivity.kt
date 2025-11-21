package com.cgadoury.onthebench

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cgadoury.onthebench.api.NhlApi
import com.cgadoury.onthebench.mvvm.TeamsViewModel
import com.cgadoury.onthebench.db.AppDatabase
import com.cgadoury.onthebench.destinations.Destination
import com.cgadoury.onthebench.mvvm.GamesViewModel
import com.cgadoury.onthebench.navigation.BottomNavBar
import com.cgadoury.onthebench.repository.PlayerRepository
import com.cgadoury.onthebench.repository.PlayerRepositoryImpl
import com.cgadoury.onthebench.repository.TeamRepository
import com.cgadoury.onthebench.repository.TeamRepositoryImpl
import com.cgadoury.onthebench.screens.GamesScreen
import com.cgadoury.onthebench.screens.PlayersScreen
import com.cgadoury.onthebench.screens.TeamDetailScreen
import com.cgadoury.onthebench.screens.TeamsScreen
import com.cgadoury.onthebench.ui.theme.OnTheBenchTheme
import com.cgadoury.onthebench.mvvm.PlayersViewModel
import com.cgadoury.onthebench.repository.GameRepository
import com.cgadoury.onthebench.repository.GameRepositoryImpl
import com.cgadoury.onthebench.screens.PlayerDetailScreen
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Purpose - main activity - the apps main activity
 */
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnTheBenchTheme {
                val navController = rememberNavController()
                val db = AppDatabase.getInstance(applicationContext)
                val api = NhlApi.retrofitService
                val teamRepository: TeamRepository = TeamRepositoryImpl(api, db.teamDao())
                val playerRepository: PlayerRepository = PlayerRepositoryImpl(api)
                val gameRepository: GameRepository = GameRepositoryImpl(api)
                val teamsViewModel by lazy {
                    TeamsViewModel(teamRepository = teamRepository)
                }
                val playersViewModel by lazy {
                    PlayersViewModel(playerRepository = playerRepository)
                }
                val gamesViewModel by lazy {
                    GamesViewModel(gameRepository = gameRepository)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "OnTheBench") },
                            colors = TopAppBarColors(
                                Color.Transparent,
                                scrolledContainerColor = Color.White,
                                navigationIconContentColor = Color.White,
                                titleContentColor = Color.Black,
                                actionIconContentColor = Color.White
                            )
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
                            PlayersScreen(
                                modifier = Modifier,
                                playersViewModel = playersViewModel,
                                navController = navController
                            )
                        }

                        composable(Destination.Games.route) {
                            GamesScreen(
                                modifier = Modifier,
                                gamesViewModel = gamesViewModel,
                                navController = navController
                                )
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
                                    team = team!!,
                                    teamsViewModel = teamsViewModel,
                                    navController = navController
                                )
                            }
                        }

                        composable(Destination.PlayerDetail.route) { navBackStackEntry ->
                            val player by playersViewModel.player
                            val playerId = navBackStackEntry.arguments?.getString("playerId")

                            Log.i("Player Id", playerId.toString())

                            LaunchedEffect(playerId) {
                                playersViewModel.getPlayerById(playerId = playerId?.toInt()!!)
                            }

                            player?.let {
                                PlayerDetailScreen(
                                    modifier = Modifier,
                                    player = player!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
