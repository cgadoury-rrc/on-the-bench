package com.cgadoury.onthebench.api.model.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.NhlApi
import com.cgadoury.onthebench.api.model.standing.Standing
import com.cgadoury.onthebench.api.model.standing.TeamAbbrev
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

/**
 * Purpose - Standing Manager - manages NHL standings data
 */
@OptIn(DelicateCoroutinesApi::class)
class StandingsViewModel(): ViewModel() {

    private var _standingsResponse = mutableStateOf<List<Standing?>>(emptyList())
    val standingsResponse: State<List<Standing?>> = _standingsResponse

    private var _teamAbbrevs = mutableStateOf<List<String>>(emptyList())
    var teamAbbreviations = _teamAbbrevs

    private var _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        getCurrentStandings()
    }

    /**
     *
     */
    private fun getCurrentStandings() {

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = NhlApi.retrofitService.getCurrentStandings()
                if (response.isSuccessful) {
                    _standingsResponse.value = response.body()?.standings ?: emptyList()
                    extractTeamAbbreviations()

                    viewModelScope.launch {
//                        teamDao.insertAllTeams(_standingsResponse.value.filterNotNull())
                    }

                    Log.i("Data", "Standings data is ready to use.")

                    Log.i("DataStream", _standingsResponse.value.toString())
                }
            } catch(e: Exception) {
                Log.e("StandingsError", e.toString())
            } finally {
                _isLoading.value = false
            }
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