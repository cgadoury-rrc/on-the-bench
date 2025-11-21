package com.cgadoury.onthebench.repository

import com.cgadoury.onthebench.api.model.game.Game

interface GameRepository {

    /**
     * Purpose - get all games - get nhl games for yesterday, today, and tomorrow
     * @return Triple<List<Game>, List<Game>, List<Game>>
     */
    suspend fun getAllGames(): Triple<List<Game>, List<Game>, List<Game>>
}