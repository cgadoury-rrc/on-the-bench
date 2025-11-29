package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Badge(
    @Json(name = "logoUrl")
    var logoUrl: LogoUrl?=null,
    @Json(name = "title")
    var title: Title?=null
)