package com.cgadoury.onthebench.api.model.point


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PointsData(
    @Json(name = "points")
    var points: List<Point?>?
)