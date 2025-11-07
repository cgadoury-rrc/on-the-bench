package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clock(
    @Json(name = "inIntermission")
    var inIntermission: Boolean?,
    @Json(name = "running")
    var running: Boolean?,
    @Json(name = "secondsRemaining")
    var secondsRemaining: Int?,
    @Json(name = "timeRemaining")
    var timeRemaining: String?
)