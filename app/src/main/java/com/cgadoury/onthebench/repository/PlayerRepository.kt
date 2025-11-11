package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.point.Point
import com.cgadoury.onthebench.api.model.point.PointsData

interface PlayerRepository {
    suspend fun getTopPlayers(): List<Point>

    suspend fun getPlayerById(playerId: Int): Player?
}