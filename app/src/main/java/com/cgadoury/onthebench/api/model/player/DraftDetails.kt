package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DraftDetails(
    @Json(name = "overallPick")
    var overallPick: Int=0,
    @Json(name = "pickInRound")
    var pickInRound: Int=0,
    @Json(name = "round")
    var round: Int=0,
    @Json(name = "teamAbbrev")
    var teamAbbrev: String="",
    @Json(name = "year")
    var year: Int=0
)