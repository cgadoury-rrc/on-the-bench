package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DraftDetails(
    @Json(name = "overallPick")
    var overallPick: Int?,
    @Json(name = "pickInRound")
    var pickInRound: Int?,
    @Json(name = "round")
    var round: Int?,
    @Json(name = "teamAbbrev")
    var teamAbbrev: String?,
    @Json(name = "year")
    var year: Int?
)