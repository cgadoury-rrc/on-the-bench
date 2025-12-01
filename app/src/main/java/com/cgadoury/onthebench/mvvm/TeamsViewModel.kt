package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.repository.TeamRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var _teamRoster = mutableStateOf<RosterData?>(RosterData())
    val teamRoster: State<RosterData?> = _teamRoster

    private var _favouriteTeamsResponse = mutableStateOf<List<Standing>>(emptyList())
    val favouriteTeamsResponse = _favouriteTeamsResponse

    private val _isFavouriteState = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val isFavouriteIconState = _isFavouriteState.asStateFlow()

    init {
        loadTeams()
    }

    /**
     * Purpose - load teams - load teams using the teams repository
     * @return Unit
     */
    private fun loadTeams(): Unit {
        viewModelScope.launch {
            _standingsResponse.value = teamRepository.getAllTeams()
        }
    }

    /**
     * Purpose - get current team roster - gets a teams current roster
     * @param teamAbbrev: The team abbreviation
     * @return Unit
     */
    fun getCurrentTeamRoster(teamAbbrev: String): Unit {
        viewModelScope.launch {
            _teamRoster.value = teamRepository.getCurrentTeamRoster(teamAbbrev = teamAbbrev)
        }
    }

    /**
     * Purpose - get team by abbreviation - get a team by its abbreviation
     * @return Unit
     */
    fun getTeamByAbbreviation(teamAbbrev: String): Standing {
        return _standingsResponse.value.find { it.teamAbbrev.default == teamAbbrev } ?: Standing()
    }

    /**
     * Purpose - update is favourite team state - updates the is favourite state of a team
     * @param teamAbbrev: The team with the team abbreviation to update
     * @return Unit
     */
    fun updateIsFavouriteTeamState(teamAbbrev: String) {
        viewModelScope.launch {
            val team =
                _standingsResponse.value.find { it.teamAbbrev.default == teamAbbrev } ?: Standing()

            team.isFavourite = !team.isFavourite

            launch(Dispatchers.IO) {
                teamRepository.updateIsFavouriteTeamState(team = team)

                val updatedMap = _isFavouriteState.value.toMutableMap()
                updatedMap[teamAbbrev] = team.isFavourite
                _isFavouriteState.value = updatedMap
            }
        }
    }

    /**
     * Purpose - get favourite teams - gets favourite teams from the firestore db
     * @return Unit
     */
    fun getFavouriteTeams(): Unit {
        viewModelScope.launch {
            _favouriteTeamsResponse.value = teamRepository.getFavouriteTeams()
        }
    }

    /**
     * Purpose - save favourite team - save a favourite team to the firestore db
     * @param team: The team to save
     * @return Unit
     */
    fun saveFavouriteTeam(team: Standing): Unit {
        viewModelScope.launch {
            teamRepository.saveFavouriteTeam(team = team)
        }
    }

    /**
     * Purpose - delete favourite team - delete a favourite team from the firestore db
     * @param teamAbbrev: The team abbreviation used to search for and delete a team
     * @return Unit
     */
    fun deleteFavouriteTeam(teamAbbrev: String) {
        viewModelScope.launch {
            teamRepository.deleteFavouriteTeam(teamAbbrev = teamAbbrev)
        }
    }
}