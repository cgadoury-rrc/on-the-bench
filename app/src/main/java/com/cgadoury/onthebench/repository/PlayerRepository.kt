package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.api.model.point.PointsData

interface PlayerRepository {

    /**
     * Purpose - get top players - get top nhl player in terms of points
     * @return List<Point>
     */
    suspend fun getTopPlayers(): List<Point>

    /**
     * Purpose - get player by id - get a player by id
     * @param playerId: The player id to search
     * @return Player?
     */
    suspend fun getPlayerById(playerId: Int): Player?
}