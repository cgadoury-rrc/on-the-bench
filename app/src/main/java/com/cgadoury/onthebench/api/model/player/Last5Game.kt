package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Last5Game(
    @Json(name = "assists")
    var assists: Int?,
    @Json(name = "gameDate")
    var gameDate: String?,
    @Json(name = "gameId")
    var gameId: Int?,
    @Json(name = "gameTypeId")
    var gameTypeId: Int?,
    @Json(name = "goals")
    var goals: Int?,
    @Json(name = "homeRoadFlag")
    var homeRoadFlag: String?,
    @Json(name = "opponentAbbrev")
    var opponentAbbrev: String?,
    @Json(name = "pim")
    var pim: Int?,
    @Json(name = "plusMinus")
    var plusMinus: Int?,
    @Json(name = "points")
    var points: Int?,
    @Json(name = "powerPlayGoals")
    var powerPlayGoals: Int?,
    @Json(name = "shifts")
    var shifts: Int?,
    @Json(name = "shorthandedGoals")
    var shorthandedGoals: Int?,
    @Json(name = "shots")
    var shots: Int?,
    @Json(name = "teamAbbrev")
    var teamAbbrev: String?,
    @Json(name = "toi")
    var toi: String?
)