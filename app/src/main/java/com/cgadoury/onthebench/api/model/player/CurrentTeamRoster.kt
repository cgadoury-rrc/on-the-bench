package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentTeamRoster(
    @Json(name = "firstName")
    var firstName: FirstName?,
    @Json(name = "lastName")
    var lastName: LastNameX?,
    @Json(name = "playerId")
    var playerId: Int?,
    @Json(name = "playerSlug")
    var playerSlug: String?
)