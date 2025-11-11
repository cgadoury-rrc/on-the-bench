package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegularSeasonX(
    @Json(name = "career")
    var career: Career?,
    @Json(name = "subSeason")
    var subSeason: SubSeason?
)