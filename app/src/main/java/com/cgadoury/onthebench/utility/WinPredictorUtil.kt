package com.cgadoury.onthebench.utility

import android.util.Log
import com.cgadoury.onthebench.api.model.game.Game
import com.cgadoury.onthebench.mvvm.TeamsViewModel

fun predictGameWinner(
    game: Game,
    teamsViewModel: TeamsViewModel
    ) {
        Log.i("Game", game.homeTeam?.abbrev.toString())
        val homeTeam = teamsViewModel.getTeamByAbbreviation(game.homeTeam?.abbrev.toString())
        val awayTeam = teamsViewModel.getTeamByAbbreviation(game.awayTeam?.abbrev.toString())
        val

        Log.i("Team", homeTeam.toString())
        Log.i("Team", awayTeam.toString())
}