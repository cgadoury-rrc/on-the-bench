package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.game.Game
import com.cgadoury.onthebench.api.model.game.GameData
import com.cgadoury.onthebench.repository.GameRepository
import kotlinx.coroutines.launch

/**
 * Purpose - games view model - handle game data between ui and data layers
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
        loadGameData()
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
}