package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Season(
    @Json(name = "assists")
    var assists: Int?,
    @Json(name = "blockedShots")
    var blockedShots: Int?,
    @Json(name = "gameTypeId")
    var gameTypeId: Int?,
    @Json(name = "gamesPlayed")
    var gamesPlayed: Int?,
    @Json(name = "goals")
    var goals: Int?,
    @Json(name = "hits")
    var hits: Int?,
    @Json(name = "pim")
    var pim: Int?,
    @Json(name = "plusMinus")
    var plusMinus: Int?,
    @Json(name = "points")
    var points: Int?,
    @Json(name = "seasonId")
    var seasonId: Int?
)