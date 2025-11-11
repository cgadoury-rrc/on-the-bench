package com.cgadoury.onthebench.api.model.player


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Player(
    @Json(name = "awards")
    var awards: List<Award>?=null,
    @Json(name = "badges")
    var badges: List<Badge>?=null,
    @Json(name = "birthCity")
    var birthCity: BirthCity?=null,
    @Json(name = "birthCountry")
    var birthCountry: String="",
    @Json(name = "birthDate")
    var birthDate: String="",
    @Json(name = "birthStateProvince")
    var birthStateProvince: BirthStateProvince?=null,
    @Json(name = "careerTotals")
    var careerTotals: CareerTotals?=null,
    @Json(name = "currentTeamAbbrev")
    var currentTeamAbbrev: String="",
    @Json(name = "currentTeamId")
    var currentTeamId: Int=0,
    @Json(name = "currentTeamRoster")
    var currentTeamRoster: List<CurrentTeamRoster> = emptyList(),
    @Json(name = "draftDetails")
    var draftDetails: DraftDetails?=null,
    @Json(name = "featuredStats")
    var featuredStats: FeaturedStats?=null,
    @Json(name = "firstName")
    var firstName: FirstNameX?=null,
    @Json(name = "fullTeamName")
    var fullTeamName: FullTeamName?=null,
    @Json(name = "headshot")
    var headshot: String="",
    @Json(name = "heightInCentimeters")
    var heightInCentimeters: Int=0,
    @Json(name = "heightInInches")
    var heightInInches: Int=0,
    @Json(name = "heroImage")
    var heroImage: String="",
    @Json(name = "inHHOF")
    var inHHOF: Int=0,
    @Json(name = "inTop100AllTime")
    var inTop100AllTime: Int=0,
    @Json(name = "isActive")
    var isActive: Boolean=true,
    @Json(name = "last5Games")
    var last5Games: List<Last5Game> = emptyList(),
    @Json(name = "lastName")
    var lastName: LastNameX?=null,
    @Json(name = "playerId")
    var playerId: Int=0,
    @Json(name = "playerSlug")
    var playerSlug: String="",
    @Json(name = "position")
    var position: String="",
    @Json(name = "seasonTotals")
    var seasonTotals: List<SeasonTotal> = emptyList(),
    @Json(name = "shootsCatches")
    var shootsCatches: String="",
    @Json(name = "shopLink")
    var shopLink: String="",
    @Json(name = "sweaterNumber")
    var sweaterNumber: Int=0,
    @Json(name = "teamCommonName")
    var teamCommonName: TeamCommonNameX?=null,
    @Json(name = "teamLogo")
    var teamLogo: String="",
    @Json(name = "teamPlaceNameWithPreposition")
    var teamPlaceNameWithPreposition: TeamPlaceNameWithPrepositionX?=null,
    @Json(name = "twitterLink")
    var twitterLink: String="",
    @Json(name = "watchLink")
    var watchLink: String="",
    @Json(name = "weightInKilograms")
    var weightInKilograms: Int=0,
    @Json(name = "weightInPounds")
    var weightInPounds: Int=0
)