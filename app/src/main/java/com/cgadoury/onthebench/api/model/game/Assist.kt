package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Assist(
    @Json(name = "assistsToDate")
    var assistsToDate: Int?,
    @Json(name = "name")
    var name: NameXX?,
    @Json(name = "playerId")
    var playerId: Int?
)