package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.api.model.point.PointsData

/**
 * Purpose - player repository impl - the player repository implementation
 * @param nhlApiService: The service to communicate with the nhl api
 */
class PlayerRepositoryImpl(
    private val nhlApiService: NhlApiService
): PlayerRepository {
    private var topPlayers: List<Point> = emptyList()

    /**
     * Purpose - get top players - get top nhl player in terms of points
     * @return List<Point>
     */
    override suspend fun getTopPlayers(): List<Point> {
        topPlayers = nhlApiService.getTop50SkaterPoints().body()?.points?.filterNotNull() ?: emptyList()

        return topPlayers
    }

    /**
     * Purpose - get player by id - get a player by id
     * @param playerId: The player id to search
     * @return Player?
     */
    override suspend fun getPlayerById(playerId: Int): Player? {
        return nhlApiService.getPlayerById(playerId).body()
    }
}