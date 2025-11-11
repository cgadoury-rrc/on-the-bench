package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.repository.PlayerRepository
import kotlinx.coroutines.launch

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

    private fun loadTopPlayers() {
        viewModelScope.launch {
            _topPlayersResponse.value = playerRepository.getTopPlayers()
        }
    }

    fun getPlayer(playerId: Int) {
        viewModelScope.launch {
            _player.value = playerRepository.getPlayerById(playerId)
        }
    }
}