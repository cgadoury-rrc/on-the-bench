package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FirstName(
    @Json(name = "cs")
    var cs: String="",
    @Json(name = "default")
    var default: String="",
    @Json(name = "fi")
    var fi: String="",
    @Json(name = "sk")
    var sk: String=""
)