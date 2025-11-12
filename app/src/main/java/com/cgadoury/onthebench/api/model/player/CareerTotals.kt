package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CareerTotals(
    @Json(name = "playoffs")
    var playoffs: Playoffs?,
    @Json(name = "regularSeason")
    var regularSeason: RegularSeason?
)