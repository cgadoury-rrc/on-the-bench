package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Career(
    @Json(name = "assists")
    var assists: Int=0,
    @Json(name = "gameWinningGoals")
    var gameWinningGoals: Int=0,
    @Json(name = "gamesPlayed")
    var gamesPlayed: Int=0,
    @Json(name = "goals")
    var goals: Int=0,
    @Json(name = "otGoals")
    var otGoals: Int=0,
    @Json(name = "pim")
    var pim: Int=0,
    @Json(name = "plusMinus")
    var plusMinus: Int=0,
    @Json(name = "points")
    var points: Int=0,
    @Json(name = "powerPlayGoals")
    var powerPlayGoals: Int=0,
    @Json(name = "powerPlayPoints")
    var powerPlayPoints: Int=0,
    @Json(name = "shootingPctg")
    var shootingPctg: Double=0.0,
    @Json(name = "shorthandedGoals")
    var shorthandedGoals: Int=0,
    @Json(name = "shorthandedPoints")
    var shorthandedPoints: Int=0,
    @Json(name = "shots")
    var shots: Int=0
)