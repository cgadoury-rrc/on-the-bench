package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SeasonTotal(
    @Json(name = "assists")
    var assists: Int?,
    @Json(name = "avgToi")
    var avgToi: String?,
    @Json(name = "faceoffWinningPctg")
    var faceoffWinningPctg: Double?,
    @Json(name = "gameTypeId")
    var gameTypeId: Int?,
    @Json(name = "gameWinningGoals")
    var gameWinningGoals: Int?,
    @Json(name = "gamesPlayed")
    var gamesPlayed: Int?,
    @Json(name = "goals")
    var goals: Int?,
    @Json(name = "leagueAbbrev")
    var leagueAbbrev: String?,
    @Json(name = "otGoals")
    var otGoals: Int?,
    @Json(name = "pim")
    var pim: Int?,
    @Json(name = "plusMinus")
    var plusMinus: Int?,
    @Json(name = "points")
    var points: Int?,
    @Json(name = "powerPlayGoals")
    var powerPlayGoals: Int?,
    @Json(name = "powerPlayPoints")
    var powerPlayPoints: Int?,
    @Json(name = "season")
    var season: Int?,
    @Json(name = "sequence")
    var sequence: Int?,
    @Json(name = "shootingPctg")
    var shootingPctg: Double?,
    @Json(name = "shorthandedGoals")
    var shorthandedGoals: Int?,
    @Json(name = "shorthandedPoints")
    var shorthandedPoints: Int?,
    @Json(name = "shots")
    var shots: Int?,
    @Json(name = "teamCommonName")
    var teamCommonName: TeamCommonName?,
    @Json(name = "teamName")
    var teamName: TeamName?,
    @Json(name = "teamPlaceNameWithPreposition")
    var teamPlaceNameWithPreposition: TeamPlaceNameWithPrepositionX?
)