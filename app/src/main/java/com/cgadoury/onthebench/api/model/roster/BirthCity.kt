package com.cgadoury.onthebench.api.model.roster


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BirthCity(
    @Json(name = "default")
    var default: String?,
    @Json(name = "fr")
    var fr: String?,
    @Json(name = "sk")
    var sk: String?
)