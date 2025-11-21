package com.cgadoury.onthebench.api.model.roster


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RosterData(
    @Json(name = "defensemen")
    var defensemen: List<Defensemen> = emptyList(),
    @Json(name = "forwards")
    var forwards: List<Forward> = emptyList(),
    @Json(name = "goalies")
    var goalies: List<Goaly> = emptyList()
)