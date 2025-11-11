package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Award(
    @Json(name = "seasons")
    var seasons: List<Season?>?,
    @Json(name = "trophy")
    var trophy: Trophy?
)