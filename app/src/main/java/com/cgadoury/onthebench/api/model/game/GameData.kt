package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameData(
    @Json(name = "currentDate")
    var currentDate: String="",
    @Json(name = "gameWeek")
    var gameWeek: List<GameWeek> = emptyList(),
    @Json(name = "games")
    var games: List<Game> = emptyList(),
    @Json(name = "nextDate")
    var nextDate: String="",
    @Json(name = "oddsPartners")
    var oddsPartners: List<OddsPartner> = emptyList(),
    @Json(name = "prevDate")
    var prevDate: String=""
)