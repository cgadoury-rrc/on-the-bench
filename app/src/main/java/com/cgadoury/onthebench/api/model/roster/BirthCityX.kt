package com.cgadoury.onthebench.api.model.roster


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BirthCityX(
    @Json(name = "cs")
    var cs: String?,
    @Json(name = "de")
    var de: String?,
    @Json(name = "default")
    var default: String?,
    @Json(name = "fi")
    var fi: String?,
    @Json(name = "fr")
    var fr: String?,
    @Json(name = "sk")
    var sk: String?,
    @Json(name = "sv")
    var sv: String?
)