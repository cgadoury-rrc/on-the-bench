package com.cgadoury.onthebench.api.model.game


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Game(
    @Json(name = "awayTeam")
    var awayTeam: AwayTeam?,
    @Json(name = "clock")
    var clock: Clock?,
    @Json(name = "condensedGame")
    var condensedGame: String?,
    @Json(name = "condensedGameFr")
    var condensedGameFr: String?,
    @Json(name = "easternUTCOffset")
    var easternUTCOffset: String?,
    @Json(name = "gameCenterLink")
    var gameCenterLink: String?,
    @Json(name = "gameDate")
    var gameDate: String?,
    @Json(name = "gameOutcome")
    var gameOutcome: GameOutcome?,
    @Json(name = "gameScheduleState")
    var gameScheduleState: String?,
    @Json(name = "gameState")
    var gameState: String?,
    @Json(name = "gameType")
    var gameType: Int?,
    @Json(name = "goals")
    var goals: List<Goal>?,
    @Json(name = "homeTeam")
    var homeTeam: HomeTeam?,
    @Json(name = "id")
    var id: Int?,
    @Json(name = "neutralSite")
    var neutralSite: Boolean?,
    @Json(name = "period")
    var period: Int?,
    @Json(name = "periodDescriptor")
    var periodDescriptor: PeriodDescriptorX?,
    @Json(name = "season")
    var season: Int?,
    @Json(name = "startTimeUTC")
    var startTimeUTC: String?,
    @Json(name = "threeMinRecap")
    var threeMinRecap: String?,
    @Json(name = "threeMinRecapFr")
    var threeMinRecapFr: String?,
    @Json(name = "tvBroadcasts")
    var tvBroadcasts: List<TvBroadcast>?,
    @Json(name = "venue")
    var venue: Venue?,
    @Json(name = "venueTimezone")
    var venueTimezone: String?,
    @Json(name = "venueUTCOffset")
    var venueUTCOffset: String?
)