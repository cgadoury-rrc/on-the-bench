package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.repository.TeamRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

/**
 * Purpose - teams view model - handle team data between ui and data layers
 */
@OptIn(DelicateCoroutinesApi::class)
class TeamsViewModel(
    private val teamRepository: TeamRepository
): ViewModel() {

    private var _standingsResponse = mutableStateOf<List<Standing>>(emptyList())
    val standingsResponse: State<List<Standing>> = _standingsResponse

    private var _team = mutableStateOf<Standing?>(Standing())
    val team: State<Standing?> = _team

    private var _teamRoster = mutableStateOf<RosterData?>(RosterData())
    val teamRoster: State<RosterData?> = _teamRoster

    private var _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        loadTeams()
    }

    /**
     * Purpose - load teams - load teams using the teams repository
     * @return Unit
     */
    private fun loadTeams() {
        viewModelScope.launch {
            _standingsResponse.value = teamRepository.getAllTeams()
        }
    }

    /**
     * Purpose - get team by abbreviation - get a team by its abbreviation
     * @return Unit
     */
    fun getTeamByAbbreviation(teamAbbrev: String) {
        viewModelScope.launch {
            _team.value = teamRepository.getTeamByAbbreviation(teamAbbrev = teamAbbrev)
        }
    }

    fun getCurrentTeamRoster(teamAbbrev: String) {
        viewModelScope.launch {
            _teamRoster.value = teamRepository.getCurrentTeamRoster(teamAbbrev = teamAbbrev)
        }
    }
}