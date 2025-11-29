package com.cgadoury.onthebench.repository

import android.util.Log
import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.db.PlayerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Purpose - player repository impl - the player repository implementation
 * @param nhlApiService: The service to communicate with the nhl api
 */
class PlayerRepositoryImpl(
    private val nhlApiService: NhlApiService,
    private val playerDao: PlayerDao
): PlayerRepository {
    private var topPlayers: List<Point> = emptyList()

    /**
     * Purpose - get top players - get top nhl player in terms of points
     * @return List<Point>: A list of the top nhl players in terms of points
     */
    override suspend fun getTopPlayers(): List<Point> {
        topPlayers = nhlApiService.getTop50SkaterPoints().body()?.points?.filterNotNull() ?: emptyList()

        return topPlayers
    }

    /**
     * Purpose - get player by id - get a player by id
     * @param playerId: The player id to search
     * @return Player?: The player to be updated
     */
    override suspend fun getPlayerById(playerId: Int): Player? {
        var player = withContext(Dispatchers.IO){
            playerDao.getPlayerById(playerId = playerId)
        }
        val currentTime = System.currentTimeMillis()
        val isPlayerExpired =
            player?.lastUpdated == null
                    || currentTime - player.lastUpdated > 43200000L

        if (player == null || isPlayerExpired) {
            try {
                player = nhlApiService.getPlayerById(playerId).body()
                withContext(Dispatchers.IO){
                    playerDao.insertPlayer(player = player!!)
                    Log.i("Data", "Saving player to db")
                }
            } catch (e: Exception) {
                Log.i("Data", "Could not retrieve player data", e)
            }
        } else {
            Log.i("Data", "Player found in database")
        }

        return player
    }

    /**
     * Purpose - update is favourite state - updates the is favourite state of a player
     * using the player dao
     * @param player: The player to update
     * @return Unit
     */
    override suspend fun updateIsFavouriteState(player: Player): Unit {
        withContext(Dispatchers.IO) {
            playerDao.updateIsFavouriteState(player)
        }
    }
}