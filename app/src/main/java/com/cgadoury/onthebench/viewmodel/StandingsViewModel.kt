package com.cgadoury.onthebench.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev
import com.cgadoury.onthebench.repository.TeamRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Purpose - Standing Manager - manages NHL standings data
 */
@OptIn(DelicateCoroutinesApi::class)
class StandingsViewModel(
    private val teamRepository: TeamRepository
): ViewModel() {

    private var _standingsResponse = mutableStateOf<List<Standing?>>(emptyList())
    val standingsResponse: State<List<Standing?>> = _standingsResponse

    private var _teamAbbrevs = mutableStateOf<List<String>>(emptyList())
    var teamAbbreviations = _teamAbbrevs

    private var _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        loadTeams()
    }

    /**
     *
     */
    private fun loadTeams() {
        GlobalScope.launch {
            _standingsResponse.value = teamRepository.getAllTeams()
            extractTeamAbbreviations()
        }
    }

    /**
     *
     */
    private fun extractTeamAbbreviations() {
        _teamAbbrevs.value = standingsResponse.value.mapNotNull {
            it?.teamAbbrev?.default
        }.distinct()

        Log.i("Abbrev", _teamAbbrevs.value.toString())
    }

    fun getTeamDetails(teamAbbrev: TeamAbbrev) {

    }
}