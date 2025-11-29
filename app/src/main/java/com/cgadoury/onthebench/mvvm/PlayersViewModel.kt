package com.cgadoury.onthebench.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.db.AppDatabase
import com.cgadoury.onthebench.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isFavouriteState = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val isFavouriteIconState = _isFavouriteState.asStateFlow()

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

    /**
     * Purpose - update favourite state - updates the is favourite state of a player
     * @param player: The player to update
     * @return Unit
     */
    fun updateIsFavouriteState(playerId: Int, db: AppDatabase) {
        viewModelScope.launch {
            val player = playerRepository.getPlayerById(playerId = playerId)

            if (player != null) {
                player.isFavourite = !player.isFavourite

                launch (Dispatchers.IO) {
                    playerRepository.updateIsFavouriteState(player = player)

                    val updatedMap = _isFavouriteState.value.toMutableMap()
                    updatedMap[playerId] = player.isFavourite
                    _isFavouriteState.value = updatedMap
                }
            }
        }

    }
}