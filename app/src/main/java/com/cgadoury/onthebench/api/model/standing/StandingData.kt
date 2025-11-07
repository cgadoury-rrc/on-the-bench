package com.cgadoury.onthebench.api.model.standing


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandingData(
    @Json(name = "standings")
    var standings: List<Standing?>?,
    @Json(name = "standingsDateTimeUtc")
    var standingsDateTimeUtc: String?,
    @Json(name = "wildCardIndicator")
    var wildCardIndicator: Boolean?
)