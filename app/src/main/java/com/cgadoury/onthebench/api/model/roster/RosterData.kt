package com.cgadoury.onthebench.api.model.roster


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RosterData(
    @Json(name = "defensemen")
    var defensemen: List<Defensemen>?,
    @Json(name = "forwards")
    var forwards: List<Forward>?,
    @Json(name = "goalies")
    var goalies: List<Goaly>?
)