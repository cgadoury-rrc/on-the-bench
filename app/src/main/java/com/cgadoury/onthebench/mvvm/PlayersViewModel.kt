package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.repository.PlayerRepository
import kotlinx.coroutines.launch

/**
 * Purpose - players view model - handle player data between ui and data layers
 * @param playerRepository: The repository to retrieve player data from
 * @return PlayersViewModel
 */
class PlayersViewModel(
    private val playerRepository: PlayerRepository
): ViewModel() {

    private var _topPlayersResponse = mutableStateOf<List<Point>>(emptyList())
    val topPlayersResponse: State<List<Point>> = _topPlayersResponse

    private var _player = mutableStateOf<Player?>(Player())
    val player: State<Player?> = _player

    init {
        loadTopPlayers()
    }

    /**
     * Purpose - load top players - load top 50 nhl players in terms of points
     * @return Unit
     */
    private fun loadTopPlayers() {
        viewModelScope.launch {
            _topPlayersResponse.value = playerRepository.getTopPlayers()
        }
    }

    /**
     * Purpose - get player by id - get a player by their player id
     * @return Unit
     */
    fun getPlayerById(playerId: Int) {
        viewModelScope.launch {
            _player.value = playerRepository.getPlayerById(playerId)
        }
    }
}