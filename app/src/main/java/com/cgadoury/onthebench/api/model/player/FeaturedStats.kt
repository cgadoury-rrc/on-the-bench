package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeaturedStats(
    @Json(name = "regularSeason")
    var regularSeason: RegularSeasonX= RegularSeasonX(),
    @Json(name = "season")
    var season: Int=0
)