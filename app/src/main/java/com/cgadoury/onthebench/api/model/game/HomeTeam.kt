package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeTeam(
    @Json(name = "abbrev")
    var abbrev: String?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "logo")
    var logo: String?,
    @Json(name = "name")
    var name: NameXXX?,
    @Json(name = "score")
    var score: Int?,
    @Json(name = "sog")
    var sog: Int?
)