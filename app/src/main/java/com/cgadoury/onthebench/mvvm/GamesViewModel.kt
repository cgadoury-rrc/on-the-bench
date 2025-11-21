package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cgadoury.onthebench.api.model.game.Game
import com.cgadoury.onthebench.api.model.game.GameData
import com.cgadoury.onthebench.repository.GameRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Purpose - games view model - handle game data between ui and data layers
 * @param gameRepository: The repository to retrieve game data from
 * @return GamesViewModel
 */
class GamesViewModel (
    private val gameRepository: GameRepository
): ViewModel() {

    private var _gamesTodayResponse = mutableStateOf<List<Game>>(emptyList())
    val gamesTodayResponse: State<List<Game>> = _gamesTodayResponse
    private var _gamesYesterdayResponse = mutableStateOf<List<Game>>(emptyList())
    val gamesYesterdayResponse: State<List<Game>> = _gamesYesterdayResponse
    private var _gamesTomorrowResponse = mutableStateOf<List<Game>>(emptyList())
    val gamesTomorrowResponse: State<List<Game>> = _gamesTomorrowResponse

    init {
        startSmartPolling()
    }

    /**
     * Purpose - load games today - load today's games for the ui
     * @return Unit
     */
    private fun loadGameData() {
        viewModelScope.launch {
            val (yesterday, today, tomorrow) = gameRepository.getAllGames()

            _gamesYesterdayResponse.value = yesterday
            _gamesTodayResponse.value = today
            _gamesTomorrowResponse.value = tomorrow
        }
    }

    /**
     * Purpose - start smart polling - loads game data intermittently if games are live
     * @return Unit
     */
    private fun startSmartPolling() {
        viewModelScope.launch {
            while (isActive) {
                loadGameData()
                val hasLiveGames = _gamesTodayResponse.value.any { it.gameState == "LIVE" }

                val delayTime = if (hasLiveGames) 10_000L else 60_000L
                delay(delayTime)
            }
        }
    }
}