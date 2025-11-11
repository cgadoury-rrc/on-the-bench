package com.cgadoury.onthebench.api.model.point


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamName(
    @Json(name = "default")
    var default: String?,
    @Json(name = "fr")
    var fr: String?
)