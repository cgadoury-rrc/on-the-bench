package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GameOutcome(
    @Json(name = "lastPeriodType")
    var lastPeriodType: String?,
    @Json(name = "otPeriods")
    var otPeriods: Int?
)