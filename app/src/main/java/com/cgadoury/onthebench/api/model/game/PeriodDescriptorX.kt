package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeriodDescriptorX(
    @Json(name = "maxRegulationPeriods")
    var maxRegulationPeriods: Int?,
    @Json(name = "number")
    var number: Int?,
    @Json(name = "periodType")
    var periodType: String?
)