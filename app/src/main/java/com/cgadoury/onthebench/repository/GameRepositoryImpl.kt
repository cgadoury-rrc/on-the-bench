package com.cgadoury.onthebench.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.cgadoury.onthebench.api.NhlApiService
import com.cgadoury.onthebench.api.model.game.Game
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GameRepositoryImpl(
    private val nhlApiService: NhlApiService
): GameRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * Purpose - get all games - get nhl games for yesterday, today, and tomorrow
     * @return Triple<List<Game>, List<Game>, List<Game>>
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllGames(): Triple<List<Game>, List<Game>, List<Game>> {
        return coroutineScope {
            val yesterday = async { getGamesYesterday() }
            val today = async { getGamesToday() }
            val tomorrow = async { getGamesTomorrow() }

            Triple(yesterday.await(), today.await(), tomorrow.await())
        }
    }

    /**
     * Purpose - get games today - get a list of nhl games on the current day
     * @return List<Game>
     */
    suspend fun getGamesToday(): List<Game> {
        return nhlApiService.getGamesByDate(
            today.format(dateFormat)
        ).body()?.games ?: emptyList()
    }

    /**
     * Purpose - get games yesterday - get a list of nhl games from yesterday
     * @return List<Game>
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesYesterday(): List<Game> {
        return nhlApiService.getGamesByDate(
            today.minusDays(1).format(dateFormat)
        ).body()?.games ?: emptyList()
    }

    /**
     * Purpose - get games tomorrow - get a list of nhl games for tomorrow
     * @return List<Game>
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getGamesTomorrow(): List<Game> {
        return nhlApiService.getGamesByDate(
            today.plusDays(1).format(dateFormat)
        ).body()?.games ?: emptyList()
    }
}