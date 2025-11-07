package com.cgadoury.onthebench.api

import com.cgadoury.onthebench.api.model.roster.RosterData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NhlService {

    @GET("roster/{team}/current")
    fun getCurrentRoster(@Path("team") team: String): Call<RosterData>
}