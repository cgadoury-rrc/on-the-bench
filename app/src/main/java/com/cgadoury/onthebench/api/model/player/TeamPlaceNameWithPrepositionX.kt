package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamPlaceNameWithPrepositionX(
    @Json(name = "default")
    var default: String="",
    @Json(name = "fr")
    var fr: String=""
)