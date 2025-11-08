package com.cgadoury.onthebench.destinations

/**
 * Purpose: A central place to manage routing information.
 */
open class Destination(val route: String) {

    object Teams: Destination("teams")

    object Players: Destination("players")

    object Games: Destination("games")

    object TeamDetail: Destination("teamDetail/{team}")
}