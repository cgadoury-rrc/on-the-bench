package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.game.Game

interface GameRepository {

    /**
     * Purpose - get games today - get today's nhl games
     * @return List<Game>
     */
    suspend fun getAllGames(): Triple<List<Game>, List<Game>, List<Game>>
}