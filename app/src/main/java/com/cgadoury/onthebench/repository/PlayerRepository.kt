package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.api.model.point.PointsData

interface PlayerRepository {

    /**
     * Purpose - get top players - get top nhl player in terms of points
     * @return List<Point>: A list of the top nhl players in terms of points
     */
    suspend fun getTopPlayers(): List<Point>

    /**
     * Purpose - get player by id - get a player by id
     * @param playerId: The player id to search
     * @return Player?: The player to be updated
     */
    suspend fun getPlayerById(playerId: Int): Player?

    /**
     * Purpose - update is favourite state - updates the is favourite state of a player
     * using the player dao
     * @param player: The player to update
     * @return Unit
     */
    suspend fun updateIsFavouriteState(player: Player): Unit
}