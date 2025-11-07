package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvBroadcast(
    @Json(name = "countryCode")
    var countryCode: String?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "market")
    var market: String?,
    @Json(name = "network")
    var network: String?,
    @Json(name = "sequenceNumber")
    var sequenceNumber: Int?
)