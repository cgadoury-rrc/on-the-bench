package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Season(
    @Json(name = "assists")
    var assists: Int=0,
    @Json(name = "blockedShots")
    var blockedShots: Int=0,
    @Json(name = "gameTypeId")
    var gameTypeId: Int=0,
    @Json(name = "gamesPlayed")
    var gamesPlayed: Int=0,
    @Json(name = "goals")
    var goals: Int=0,
    @Json(name = "hits")
    var hits: Int=0,
    @Json(name = "pim")
    var pim: Int=0,
    @Json(name = "plusMinus")
    var plusMinus: Int=0,
    @Json(name = "points")
    var points: Int=0,
    @Json(name = "seasonId")
    var seasonId: Int=0
)