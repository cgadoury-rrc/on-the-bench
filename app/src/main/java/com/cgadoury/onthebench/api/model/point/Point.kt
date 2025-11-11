package com.cgadoury.onthebench.api.model.point


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Point(
    @Json(name = "firstName")
    var firstName: FirstName?,
    @Json(name = "headshot")
    var headshot: String?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "lastName")
    var lastName: LastName?,
    @Json(name = "position")
    var position: String?,
    @Json(name = "sweaterNumber")
    var sweaterNumber: Int?,
    @Json(name = "teamAbbrev")
    var teamAbbrev: String?,
    @Json(name = "teamLogo")
    var teamLogo: String?,
    @Json(name = "teamName")
    var teamName: TeamName?,
    @Json(name = "value")
    var value: Int?
)