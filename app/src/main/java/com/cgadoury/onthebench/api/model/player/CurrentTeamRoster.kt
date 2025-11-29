package com.cgadoury.onthebench.api.model.player


import com.cgadoury.onthebench.api.model.game.LastName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentTeamRoster(
    @Json(name = "firstName")
    var firstName: FirstName?=null,
    @Json(name = "lastName")
    var lastName: LastNameX?=null,
    @Json(name = "playerId")
    var playerId: Int=0,
    @Json(name = "playerSlug")
    var playerSlug: String=""
)