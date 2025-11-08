package com.cgadoury.onthebench.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import com.cgadoury.onthebench.R
import com.cgadoury.onthebench.destinations.Destination

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry.value?.destination

        val ic_teams = painterResource(id=R.drawable.ic_teams)
        val ic_players = painterResource(id=R.drawable.ic_rosters)
        val ic_games = painterResource(id=R.drawable.ic_games)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Teams.route,
            onClick = {
                navController.navigate(Destination.Teams.route) {
                    popUpTo(Destination.Teams.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(painter = ic_teams, contentDescription = null) },
            label = { Text(text = Destination.Teams.route) }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Players.route,
            onClick = {
                navController.navigate(Destination.Players.route) {
                    popUpTo(Destination.Players.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(painter = ic_players, contentDescription = null) },
            label = { Text(text = Destination.Players.route) }
        )
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Games.route,
            onClick = {
                navController.navigate(Destination.Games.route) {
                    popUpTo(Destination.Games.route)
                    launchSingleTop = true
                }
            },
            icon = { Icon(painter = ic_games, contentDescription = null) },
            label = { Text(text = Destination.Games.route) }
        )
    }
}