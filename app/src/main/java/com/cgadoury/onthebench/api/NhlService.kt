package com.cgadoury.onthebench.api

import com.cgadoury.onthebench.api.model.game.GameData
import com.cgadoury.onthebench.api.model.roster.RosterData
import com.cgadoury.onthebench.api.model.standing.StandingData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Purpose - Nhl Service - an interface to interact with the Nhl Api
 */
interface NhlService {

    @GET("v1/standings/now")
    fun getCurrentStandings(): Call<StandingData>

    @GET("v1/score/now")
    fun getGamesToday(): Call<GameData>

    @GET("v1/score/{date}")
    fun getGamesByDate(@Path("date") date: String): Call<GameData>

    @GET("v1/roster/{team}/current")
    fun getCurrentRoster(@Path("team") team: String): Call<RosterData>
}