package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.api.model.point.PointsData

class PlayerRepositoryImpl(
    private val nhlApiService: NhlApiService
): PlayerRepository {
    private var topPlayers: List<Point> = emptyList()
    private var player: Player? = null

    override suspend fun getTopPlayers(): List<Point> {
        topPlayers = nhlApiService.getTop50SkaterPoints().body()?.points?.filterNotNull() ?: emptyList()

        return topPlayers
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        player = nhlApiService.getPlayerById(playerId).body()

        return player
    }
}