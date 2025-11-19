package com.cgadoury.onthebench.api

import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.game.GameData
import com.cgadoury.onthebench.api.model.point.PointsData
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.StandingData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Purpose - Nhl Service - an interface to interact with the Nhl Api
 */
interface NhlApiService {

    @GET("v1/standings/now")
    suspend fun getCurrentStandings(): Response<StandingData>

    @GET("v1/score/now")
    suspend fun getGamesToday(): Response<GameData>

    @GET("v1/score/{date}")
    suspend fun getGamesByDate(@Path("date") date: String): Response<GameData>

    @GET("v1/roster/{team}/current")
    suspend fun getCurrentRoster(@Path("team") team: String): Call<RosterData>

    @GET("v1/skater-stats-leaders/current?categories=points&limit=50")
    suspend fun getTop50SkaterPoints(): Response<PointsData>

    @GET("v1/player/{playerId}/landing")
    suspend fun getPlayerById(@Path("playerId") playerId: Int): Response<Player>
}