package com.cgadoury.onthebench.api

import com.cgadoury.onthebench.api.model.player.Player
import com.cgadoury.onthebench.api.model.game.GameData
import com.cgadoury.onthebench.api.model.point.PointsData
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.StandingData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Purpose - Nhl Service - an interface to interact with the Nhl Api
 */
interface NhlApiService {

    /**
     * Purpose - get current standings - gets the current standings from the nhl api
     * @return Response<StandingData>: current nhl standings
     */
    @GET("v1/standings/now")
    suspend fun getCurrentStandings(): Response<StandingData>

    /**
     * Purpose - get games today - gets today's nhl games from the nhl api
     * @return Response<GameData>: game data for the current day
     */
    @GET("v1/score/now")
    suspend fun getGamesToday(): Response<GameData>

    /**
     * Purpose - get games by date - gets games for a specific date
     * @param date: The date to retrieve game data for
     * @return Response<GameData>: game data for a specific data
     */
    @GET("v1/score/{date}")
    suspend fun getGamesByDate(@Path("date") date: String): Response<GameData>

    /**
     * Purpose - get current roster - get the current roster for a specific team
     * @param teamAbbrev: The team abbreviation to retrieve roster data for
     * @return Response<RosterData>: roster data for a specific team
     */
    @GET("v1/roster/{teamAbbrev}/current")
    suspend fun getCurrentRoster(@Path("teamAbbrev") teamAbbrev: String): Response<RosterData>

    /**
     * Purpose - get top 50 skater points - gets the top 50 skaters in terms of points
     * @return Response<PointsData>: points data for the top 50 nhl skaters
     */
    @GET("v1/skater-stats-leaders/current?categories=points&limit=50")
    suspend fun getTop50SkaterPoints(): Response<PointsData>

    /**
     * Purpose - get player by id - gets a player by their player id
     * @param playerId: The player id to retrieve player data for
     * @return Response<Player>: player information for a specific nhl player
     */
    @GET("v1/player/{playerId}/landing")
    suspend fun getPlayerById(@Path("playerId") playerId: Int): Response<Player>
}