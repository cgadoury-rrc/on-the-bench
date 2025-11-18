package com.cgadoury.onthebench.api.model.standing


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamAbbrev(
    @Json(name = "default")
    var default: String=""
)