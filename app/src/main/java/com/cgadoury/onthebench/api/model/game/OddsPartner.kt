package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OddsPartner(
    @Json(name = "accentColor")
    var accentColor: String?,
    @Json(name = "bgColor")
    var bgColor: String?,
    @Json(name = "country")
    var country: String?,
    @Json(name = "imageUrl")
    var imageUrl: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "partnerId")
    var partnerId: Int?,
    @Json(name = "siteUrl")
    var siteUrl: String?,
    @Json(name = "textColor")
    var textColor: String?
)