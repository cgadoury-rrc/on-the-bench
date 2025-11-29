package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Last5Game(
    @Json(name = "assists")
    var assists: Int=0,
    @Json(name = "gameDate")
    var gameDate: String="",
    @Json(name = "gameId")
    var gameId: Int=0,
    @Json(name = "gameTypeId")
    var gameTypeId: Int=0,
    @Json(name = "goals")
    var goals: Int=0,
    @Json(name = "homeRoadFlag")
    var homeRoadFlag: String="",
    @Json(name = "opponentAbbrev")
    var opponentAbbrev: String="",
    @Json(name = "pim")
    var pim: Int=0,
    @Json(name = "plusMinus")
    var plusMinus: Int=0,
    @Json(name = "points")
    var points: Int=0,
    @Json(name = "powerPlayGoals")
    var powerPlayGoals: Int=0,
    @Json(name = "shifts")
    var shifts: Int=0,
    @Json(name = "shorthandedGoals")
    var shorthandedGoals: Int=0,
    @Json(name = "shots")
    var shots: Int=0,
    @Json(name = "teamAbbrev")
    var teamAbbrev: String="",
    @Json(name = "toi")
    var toi: String=""
)