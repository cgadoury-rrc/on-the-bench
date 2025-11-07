package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Goal(
    @Json(name = "assists")
    var assists: List<Assist>?,
    @Json(name = "awayScore")
    var awayScore: Int?,
    @Json(name = "discreteClip")
    var discreteClip: Long?,
    @Json(name = "discreteClipFr")
    var discreteClipFr: Long?,
    @Json(name = "firstName")
    var firstName: FirstName?,
    @Json(name = "goalModifier")
    var goalModifier: String?,
    @Json(name = "goalsToDate")
    var goalsToDate: Int?,
    @Json(name = "highlightClip")
    var highlightClip: Long?,
    @Json(name = "highlightClipFr")
    var highlightClipFr: Long?,
    @Json(name = "highlightClipSharingUrl")
    var highlightClipSharingUrl: String?,
    @Json(name = "highlightClipSharingUrlFr")
    var highlightClipSharingUrlFr: String?,
    @Json(name = "homeScore")
    var homeScore: Int?,
    @Json(name = "lastName")
    var lastName: LastName?,
    @Json(name = "mugshot")
    var mugshot: String?,
    @Json(name = "name")
    var name: NameXX?,
    @Json(name = "period")
    var period: Int?,
    @Json(name = "periodDescriptor")
    var periodDescriptor: PeriodDescriptorX?,
    @Json(name = "playerId")
    var playerId: Int?,
    @Json(name = "strength")
    var strength: String?,
    @Json(name = "teamAbbrev")
    var teamAbbrev: String?,
    @Json(name = "timeInPeriod")
    var timeInPeriod: String?
)