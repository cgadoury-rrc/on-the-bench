package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameWeek(
    @Json(name = "date")
    var date: String="",
    @Json(name = "dayAbbrev")
    var dayAbbrev: String="",
    @Json(name = "numberOfGames")
    var numberOfGames: Int=0
)